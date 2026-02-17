import Vue from 'vue'                 // Vue2 가져오기
import App from './App.vue'           // 루트 컴포넌트
import router from './router'         // 라우터 설정 가져오기

Vue.config.productionTip = false      // 개발 팁 메시지 숨김

new Vue({
  router,                             // 라우터 연결
  render: h => h(App)                 // App.vue를 화면에 렌더링
}).$mount('#app')                     // index.html의 #app에 붙임