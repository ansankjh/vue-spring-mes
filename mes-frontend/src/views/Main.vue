<template>
  <div class="layout">
    <header class="top">
      <div class="logo">MES</div>

      <div class="right">
        <span class="user">LOGIN_ID: {{ loginId }}</span>
        <button class="logout" @click="doLogout">로그아웃</button>
      </div>
    </header>

    <div class="content">
      <h2>메인 화면</h2>
      <p>여기에 대시보드/사원목록/메뉴 카드 등을 붙여나가면 됨.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainView',
  data: function () {
    return {
      loginId: '' // 저장된 로그인 아이디 표시용
    }
  },
  created: function () {
    this.loginId = localStorage.getItem('MES_LOGIN_ID') || '' // 로컬스토리지에서 가져오기
  },
  methods: {
    doLogout: function () {
      localStorage.removeItem('MES_LOGIN')       // 로그인 상태 제거
      localStorage.removeItem('MES_LOGIN_ID')    // 아이디 제거
      this.$router.push('/login')                // 로그인 화면으로 이동
    }
  }
}
</script>

<style scoped>
.layout { min-height: 100%; background: #f5f6f8; }
.top { height: 56px; background: #111827; color: white; display: flex; align-items: center; justify-content: space-between; padding: 0 16px; }
.logo { font-weight: bold; }
.right { display: flex; align-items: center; gap: 12px; }
.user { font-size: 13px; opacity: .9; }
.logout { padding: 8px 10px; border: 0; border-radius: 8px; cursor: pointer; }
.content { padding: 16px; }
</style>