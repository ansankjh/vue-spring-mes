<template>
  <div id="app">
    <!-- 라우터가 현재 경로에 맞는 페이지(Login/Main)를 여기 꽂아줌 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App' // 컴포넌트 이름
}
</script>

<style>
/* 전체 기본 스타일(원하면 나중에 템플릿/부트스트랩 붙이면 됨) */
html, body, #app { height: 100%; margin: 0; }
body { font-family: Arial, sans-serif; }
</style>