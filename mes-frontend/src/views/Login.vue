<template>
  <div class="wrap">
    <div class="card">
      <h2 class="title">MES Login</h2>

         <!-- 아이디 한 줄 -->
        <div class="form-row">
            <input class="input" v-model="loginId" placeholder="아이디를 입력해주세요." @keyup.enter="doLogin" />
        </div>

        <!-- 비밀번호 한 줄 -->
        <div class="form-row">
            <input class="input" type="password" v-model="password" placeholder="비밀번호를 입력해주세요." @keyup.enter="doLogin" />
        </div>

        <div>
            <button class="btn" @click="doLogin">로그인</button>
        </div>

        <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
    </div>
  </div>
</template>

<script>
// auth.js login 함수 import
import { login } from "@/api/auth";

export default {
  name: 'LoginView',               // 컴포넌트 이름

  data: function () {          // Vue2 옵션 문법(함수로 반환)
    return {
      loginId: "",             // 입력된 아이디
      password: "",             // 입력된 비밀번호
      errorMsg: ""              // 로그인 실패/오류 메시지
    }
  },

  watch: {
    loginId() {
      if (this.errorMsg) this.errorMsg = "";
    },

    password() {
      if (this.errorMsg) this.errorMsg = "";
    }
  },

  methods: { // vue 메소드
    // 아이디, 비밀번호 공백체크 에러메시지가 아이디나 비밀번호를 사용자가 입력하면 메시지 지움
    // 로그인 실행
    doLogin() {
      this.errorMsg = ""; // 이전 에러 메시지 초기화

      // 아이디 공백 체크
      if(!this.loginId || this.loginId.trim() === "") {
        this.errorMsg = "아이디를 입력하세요.";
        return;
      }

      // 비밀번호 공백 체크
      if(!this.password || this.password.trim() === "") {
        this.errorMsg = "비밀번호를 입력해주세요.";
        return;
      }

      //this.$router.push("/equipment");
      // 서버 로그인 호출
      login(this.loginId, this.password)
        .then(() => {
          localStorage.setItem('isLoggedIn', 'true');
          this.$router.push("/"); //
        })
        .catch((err) => {
          if (err && err.response && err.response.status === 401) {
            this.errorMsg = "아이디 또는 비밀번호가 올바르지 않습니다.";
          } else {
            this.errorMsg = "로그인 중 오류가 발생했습니다."
          }
        });        
    }
  }
}
</script>

<style scoped>
.wrap {
    height: 100%;
    display: flex; 
    align-items: center; 
    justify-content: center; 
    background: #f5f6f8; 
}

.card { 
    width: 360px; 
    background: white; 
    padding: 24px; 
    border-radius: 12px; 
    box-shadow: 0 8px 24px rgba(0,0,0,.08); 
}

.title { 
    margin: 0 0 16px;
    text-align: center;
 }

 .form-row {
    display: flex;          /* 라벨+인풋을 한 줄로 */
    align-items: center;    /* 세로 중앙 정렬 */
    gap: 10px;              /* 라벨-인풋 간격 */
    margin: 10px 0;         /* 줄 간격 */
}

.label { 
    width: 70px;            /* 라벨 고정폭(원하면 조절) */
    margin: 0;              /* 기존 margin 제거 */
    font-size: 13px;
    color: #444;
    text-align: right;      /* 라벨 글자 오른쪽 정렬(원하면 left) */ 
}

.input { 
    flex: 1;                /* 남은 공간을 인풋이 다 차지 */
    padding: 10px 12px;
    border: 1px solid #ddd;
    border-radius: 8px;
    outline: none;
}

.input:focus { 
    border-color: #bbb; 
}

.btn-row {
  padding-left: 80px;  /* label(70) + gap(10) */
  margin-top: 14px;
}

.btn {
  width: 100%;         /* ✅ 인풋 영역과 동일 폭 */
  padding: 10px 12px;
  border: 0;
  border-radius: 8px;
  cursor: pointer;
}

.hint {
    margin-top: 10px; 
    font-size: 12px; 
    color: #777; 
}
</style>