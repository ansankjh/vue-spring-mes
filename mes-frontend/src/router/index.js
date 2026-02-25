import Vue from 'vue'                          // Vue2
import Router from 'vue-router'                // Vue Router

import LoginView from '@/views/Login.vue'         // 로그인 페이지
import MainView from '@/views/Main.vue'           // 메인 페이지
import EquipmentDashBoardView from '@/views/EquipmentDashboard.vue'

import { fetchMe } from '@/api/auth.js' // auth.js에 있는 fetchMe() 가져오기 import

Vue.use(Router)                                // Vue에 Router 플러그인 등록

const router = new Router({
  mode: 'history',                             // 주소에 # 제거 (원치 않으면 'hash'로)
  routes: [
    {
      path: '/login',                          // 로그인 화면 경로
      name: 'Login',
      component: LoginView,
      meta: { public: true } // 로그인은 세션 체크 없이 접근 허용
    },
    {
      path: '/mainHold',                               // 메인 화면 경로
      name: 'Main',
      component: MainView,
      meta: { requiresAuth: true }             // 로그인 필요 표시(가드에서 사용)
    },
    {
      path: '*',                               // 없는 주소 들어오면
      redirect: '/'                            // 메인으로 보냄
    },
    {
      path: '/',
      name: 'EquipmentDashBoard',
      component: EquipmentDashBoardView,
      meta: { requiresAuth: true }
    }
  ]
})

// 이미 한 번 fetchMe()로 세션 확인했으면, 같은 실행(새로고침 전) 동안은 또 확인X
let meChecked = false

export function setMeChecked(value) {
  meChecked = value;
}

// 라우터 가드: 페이지 이동 전에 로그인 체크
// to : 이동하려는 페이지 정보 (/, /login 등)
// from : 현재 페이지 정보
// next : 이동을 허용/차단/다른곳으로 보내는 함수 next() 이동 허용, next('/login') 로그인으로 보내기, next(false) 이동 취소
router.beforeEach(async (to, from, next) => {
  console.log('[GUARD]', from.fullPath, '->', to.fullPath)

  // 로그인 성공으로 세션에 등록 되어있는 상태에서 로그인 화면으로 접근 시도시 메인페이지로 리다이렉트
  if(to.path == '/login') {
    // 로컬스토리지에 로그인 정보가 있을 때만 서버에 '진짜인지 확인'
    const hasSession = localStorage.getItem('isLoggedIn') === 'true'

    console.log("hasSession : " + hasSession);

    if(hasSession) {
      try {
      await fetchMe() // 세션있으면 200, 없으면 401
      meChecked = true;
      return next('/');
    } catch(err) {
      // 세션이 만료 되었거나 401이면 흔적 지우고 로그인 페이지 접근 허용
      localStorage.removeItem('isLoggedIn');
      meChecked = false;
      return next();
    }
    }
  }

  // 로그인이 필요없는(공개) 페이지
  if(to.matched.some(function (r) { return r.meta && r.meta.public})) {
    // meta: { public: true } 같이 되어있는 페이지 /login
    return next()
  }

  // 로그인이 필요한 페이지
  if(!to.matched.some(function (r) { return r.meta && r.meta.requiresAuth})) {
    return next()
  }

  // 이미 검증 했으면 통과
  if (meChecked) {
    return next()
  }
  
  try {
    await fetchMe()
    meChecked = true
    return next()
  } catch (err) {
    // fetchMe()에서 HTTP 상태코드 401을 던지는 구조라면 여기로 옴
    // 혹시 axios 에러 형태면 err.response.status로도 확인 가능
    const status = (err && err.response && err.response.status) || err.status
    // 401 Unauthorized : 인증이 필요(로그인/세션 없음 또는 만료)
    // 403 Forbidden : 인증은 됐지만 권한이 없음
    if(status === 401 || status === 403) {
      meChecked = false
      return next('/login');
    }
  }
})

export default router                           // 라우터를 main.js에서 쓰게 내보냄