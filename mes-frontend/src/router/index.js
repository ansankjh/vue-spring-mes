import Vue from 'vue'                          // Vue2
import Router from 'vue-router'                // Vue Router

import LoginView from '@/views/Login.vue'         // 로그인 페이지
import MainView from '@/views/Main.vue'           // 메인 페이지
import EquipmentDashBoardView from '@/views/EquipmentDashboard.vue'

import { fetchMe} from '@/api/auth.js' // auth.js에 있는 fetchMe() 가져오기 import

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
      path: '/',                               // 메인 화면 경로
      name: 'Main',
      component: MainView,
     // meta: { requiresAuth: true }             // 로그인 필요 표시(가드에서 사용)
     meta: { public: true }
    },
    {
      path: '*',                               // 없는 주소 들어오면
      redirect: '/'                            // 메인으로 보냄
    },
    {
      path: '/equipment',
      name: 'EquipmentDashBoard',
      component: EquipmentDashBoardView,
      meta: { public: true }
    }
  ]
})

// 이미 한 번 fetchMe()로 세션 확인했으면, 같은 실행(새로고침 전) 동안은 또 확인X
let meChecked = false

// 라우터 가드: 페이지 이동 전에 로그인 체크
// to : 이동하려는 페이지 정보 (/, /login 등)
// from : 현재 페이지 정보
// next : 이동을 허용/차단/다른곳으로 보내는 함수 next() 이동 허용, next('/login') 로그인으로 보내기, next(false) 이동 취소
router.beforeEach(async (to, from, next) => {
  console.log('[GUARD]', from.fullPath, '->', to.fullPath)
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
    var status = err & err.response && err.response.status
    // 401 Unauthorized : 인증이 필요(로그인/세션 없음 또는 만료)
    // 403 Forbidden : 인증은 됐지만 권한이 없음
    if(status === 401 || status === 403) {
      meChecked = false
      return next({
        path: '/login',
        query: { redirect: to.fullPath } // 보호 페이지로 이동하려는데 세션만료나 권한없음 같은 에러가 발생했을때 로그인 페이지로 가지만 로그인이 성공 됐을 경우에 가려면 페이지로 이동됨
      })
    }
  }
})

export default router                           // 라우터를 main.js에서 쓰게 내보냄