<!--
  Vue2 전용
  파일: src/views/EquipmentDashboard.vue

  기능:
  - 설비 10개 카드 표시
  - 상태코드(RUN/IDLE/DOWN/PM) -> UI 매핑(라벨/클래스)
  - 3초마다 랜덤 상태 변경(실시간 느낌 연습)
-->
<template>
  <div class="page">
    <header class="top">
      <div class="header-left">
      <div class="logo">MES</div>
      <nav class="nav">
        <div class="nav-item">
          <span class="nav-link">시스템 관리</span>
          <div class="dropdown">
            <a href="#">사용자 관리</a>
            <a href="#">권한 설정</a>
            <a href="#">코드 관리</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">생산 관리</span>
          <div class="dropdown">
            <a href="#">생산 계획</a>
            <a href="#">작업 지시</a>
            <a href="#">실적 집계</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">공정 관리</span>
          <div class="dropdown">
            <a href="#">공정 흐름도</a>
            <a href="#">설비 가동현황</a>
            <a href="#">불량 분석</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">품질 관리</span>
          <div class="dropdown">
            <a href="#">공정 흐름도</a>
            <a href="#">설비 가동현황</a>
            <a href="#">불량 분석</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">재고 관리</span>
          <div class="dropdown">
            <a href="#">공정 흐름도</a>
            <a href="#">설비 가동현황</a>
            <a href="#">불량 분석</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">설비 관리</span>
          <div class="dropdown">
            <a href="#">공정 흐름도</a>
            <a href="#">설비 가동현황</a>
            <a href="#">불량 분석</a>
          </div>
        </div>
        <div class="nav-item">
          <span class="nav-link">실적 관리</span>
          <div class="dropdown">
            <a href="#">공정 흐름도</a>
            <a href="#">설비 가동현황</a>
            <a href="#">불량 분석</a>
          </div>
        </div>
      </nav>
    </div>
      <div class="right">
        <span class="user">사용자: {{ userNm }}</span>
        <button class="logout" @click="doLogout">로그아웃</button>
      </div>
    </header>
    <div class="header">
      <h2 class="title">설비 모니터링</h2>
      <div class="sub">
        마지막 갱신: <span class="time">{{ lastRefreshText }}</span>
      </div>
    </div>

    <div class="grid">
      <div
        v-for="eqp in equipments"
        :key="eqp.eqpId"
        class="card"
        :class="getStatusUi(eqp.status).cls"
      >
        <div class="card-top">
          <div class="eqp-id">{{ eqp.eqpId }}</div>
          <div class="eqp-name">{{ eqp.eqpName }}</div>
        </div>

        <div class="status-row">
          <span class="badge">{{ getStatusUi(eqp.status).label }}</span>
          <span class="status-code">{{ eqp.status }}</span>
        </div>

        <div class="updated">
          업데이트: <span class="time">{{ eqp.lastUpdated }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 로그아웃 함수 가져오기
import { logout } from "@/api/auth";
import { fetchMe } from "@/api/auth";
import { setMeChecked } from "@/router/index";

// Vue2 옵션 API
export default {

  name: "EquipmentDashboardView",

  data: function () {
    return {
      userNm: "",
      loginId: "", // 저장된 로그인 아이디 표시용
      // 상태코드 -> UI(라벨/클래스) 매핑 (if/else 대신 테이블로 관리)
      STATUS_UI: {
        RUN: { label: "가동중", cls: "st-run" },
        IDLE: { label: "대기", cls: "st-idle" },
        DOWN: { label: "고장", cls: "st-down" },
        PM: { label: "점검", cls: "st-pm" },
      },

      // 설비 더미 10개
      equipments: [
        { eqpId: "EQP-01", eqpName: "노광기-1", status: "RUN", lastUpdated: "" },
        { eqpId: "EQP-02", eqpName: "노광기-2", status: "IDLE", lastUpdated: "" },
        { eqpId: "EQP-03", eqpName: "식각기-1", status: "DOWN", lastUpdated: "" },
        { eqpId: "EQP-04", eqpName: "식각기-2", status: "RUN", lastUpdated: "" },
        { eqpId: "EQP-05", eqpName: "증착기-1", status: "PM", lastUpdated: "" },
        { eqpId: "EQP-06", eqpName: "증착기-2", status: "RUN", lastUpdated: "" },
        { eqpId: "EQP-07", eqpName: "검사기-1", status: "IDLE", lastUpdated: "" },
        { eqpId: "EQP-08", eqpName: "검사기-2", status: "RUN", lastUpdated: "" },
        { eqpId: "EQP-09", eqpName: "세정기-1", status: "DOWN", lastUpdated: "" },
        { eqpId: "EQP-10", eqpName: "세정기-2", status: "RUN", lastUpdated: "" },
      ],

      lastRefreshAt: null, // 페이지 마지막 갱신 시간(Date)
      timerId: null, // setInterval 핸들
    };
  },

  created: function () {
    const loginInfo = this
    fetchMe().then((res) => {
      loginInfo.userNm = res.data.userNm;
    })
  },

  computed: {
    // Date -> 화면용 문자열
    lastRefreshText: function () {
      if (!this.lastRefreshAt) return "-";
      return this.formatDateTime(this.lastRefreshAt);
    },
  },

  mounted: function () {
    // 초기 시간 찍기
    this.touchAllUpdatedTime();

    // 3초마다 랜덤 상태 변경
    this.timerId = setInterval(() => {
      this.randomizeStatuses();
    }, 3000);
  },

  beforeDestroy: function () {
    // Vue2에서는 beforeDestroy에서 정리
    if (this.timerId) clearInterval(this.timerId);
  },

  methods: {
    // ====== (추가) Main.vue에서 쓰던 로그아웃 로직 ======
    doLogout: function () {
      logout()
        .then(() => {
          console.log("로그아웃 완료");
          //this.$router.push("/login");
        })
        .catch((err) => {
          if(err) {
            console.log("서버 응답 에러 발생했지만, 로컬데이터는 삭제합니다.");
          }
        })
        .finally(() => {
          localStorage.setItem('isLoggedIn', 'false');
          setMeChecked(false);
          this.$router.push("/login");
        })
    },

    // status -> UI 정보 반환
    getStatusUi: function (status) {
      return this.STATUS_UI[status] || { label: "알수없음", cls: "st-unknown" };
    },

    // 전체 설비 업데이트 시간 + 페이지 갱신시간 찍기
    touchAllUpdatedTime: function () {
      const now = new Date();
      this.lastRefreshAt = now;

      for (let i = 0; i < this.equipments.length; i++) {
        this.equipments[i].lastUpdated = this.formatDateTime(now);
      }
    },

    // 상태 랜덤 변경
    randomizeStatuses: function () {
      const statuses = ["RUN", "IDLE", "DOWN", "PM"];
      const now = new Date();
      this.lastRefreshAt = now;

      for (let i = 0; i < this.equipments.length; i++) {
        const randIndex = Math.floor(Math.random() * statuses.length);
        this.equipments[i].status = statuses[randIndex];
        this.equipments[i].lastUpdated = this.formatDateTime(now);
      }
    },

    // 날짜 포맷: YYYY-MM-DD HH:mm:ss
    formatDateTime: function (dateObj) {
      const y = dateObj.getFullYear();
      const m = this.pad2(dateObj.getMonth() + 1);
      const d = this.pad2(dateObj.getDate());
      const hh = this.pad2(dateObj.getHours());
      const mm = this.pad2(dateObj.getMinutes());
      const ss = this.pad2(dateObj.getSeconds());
      return y + "-" + m + "-" + d + " " + hh + ":" + mm + ":" + ss;
    },

    // 두 자리 문자열
    pad2: function (n) {
      return n < 10 ? "0" + n : "" + n;
    },
  },
};
</script>

<style scoped>
.top { height: 56px; 
  background: #111827; 
  color: white; 
  display: flex; 
  align-items: center; 
  justify-content: space-between; 
  padding: 0 16px; 
  position: relative;
  z-index: 100; /* 드롭다운이 컨텐츠 위로 오도록 */ 
}
.header-left {
  display: flex;
  align-items: center;
  gap: 40px; /* 로고와 메뉴 사이 간격 */
}
.logo { font-weight: bold; font-size: 20px; color: #3b82f6; }
.nav {
  display: flex;
  gap: 20px;
  height: 56px;
}

.nav-item {
  position: relative;
  display: flex;
  align-items: center;
}

.nav-link {
  cursor: pointer;
  font-size: 15px;
  font-weight: 500;
  padding: 0 10px;
  height: 100%;
  display: flex;
  align-items: center;
  transition: color 0.2s;
}

.nav-link:hover {
  color: #3b82f6;
}

/* (추가) 드롭다운 스타일 */
.dropdown {
  display: none; /* 평소에는 숨김 */
  position: absolute;
  top: 56px; /* 헤더 높이만큼 아래로 */
  left: 0;
  background-color: #ffffff;
  min-width: 140px;
  box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
  border-radius: 0 0 8px 8px;
  overflow: hidden;
}

.dropdown a {
  color: #333;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  font-size: 13px;
  border-bottom: 1px solid #f0f0f0;
}

.dropdown a:last-child { border-bottom: none; }

.dropdown a:hover {
  background-color: #f3f4f6;
  color: #3b82f6;
}

/* 마우스 올리면 드롭다운 표시 */
.nav-item:hover .dropdown {
  display: block;
}
.right { display: flex; align-items: center; gap: 12px; }
.user { font-size: 13px; opacity: .9; }
.logout { padding: 8px 10px; border: 0; border-radius: 8px; cursor: pointer; }

.page {
  padding: 16px;
}

.header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin-bottom: 12px;
  gap: 12px;
}

.title {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
}

.sub {
  font-size: 12px;
  color: #666;
}

.time {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono",
    "Courier New", monospace;
}

.grid {
  display: grid;
  grid-template-columns: repeat(5, minmax(160px, 1fr));
  gap: 12px;
}

@media (max-width: 1200px) {
  .grid {
    grid-template-columns: repeat(4, minmax(160px, 1fr));
  }
}
@media (max-width: 900px) {
  .grid {
    grid-template-columns: repeat(3, minmax(160px, 1fr));
  }
}
@media (max-width: 650px) {
  .grid {
    grid-template-columns: repeat(2, minmax(160px, 1fr));
  }
}

.card {
  border: 1px solid #e6e6e6;
  border-radius: 10px;
  padding: 12px;
  background: #fff;
}

.card-top {
  margin-bottom: 10px;
}

.eqp-id {
  font-weight: 700;
  font-size: 13px;
  color: #333;
}

.eqp-name {
  font-size: 12px;
  color: #555;
  margin-top: 4px;
}

.status-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 6px 0 10px 0;
}

.badge {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
}

.status-code {
  font-size: 12px;
  color: #777;
}

.updated {
  font-size: 12px;
  color: #666;
}

/* 상태별 스타일 */
.st-run {
  border-left: 6px solid #2ecc71;
}
.st-run .badge {
  background: rgba(46, 204, 113, 0.15);
  color: #1f7a45;
}

.st-idle {
  border-left: 6px solid #f39c12;
}
.st-idle .badge {
  background: rgba(243, 156, 18, 0.15);
  color: #8a5a00;
}

.st-down {
  border-left: 6px solid #e74c3c;
}
.st-down .badge {
  background: rgba(231, 76, 60, 0.15);
  color: #8b1e14;
}

.st-pm {
  border-left: 6px solid #3498db;
}
.st-pm .badge {
  background: rgba(52, 152, 219, 0.15);
  color: #1e5b86;
}

.st-unknown {
  border-left: 6px solid #7f8c8d;
}
.st-unknown .badge {
  background: rgba(127, 140, 141, 0.15);
  color: #4c5758;
}
</style>