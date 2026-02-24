// src/api/auth.js
// auth 관련 API만 모아두는 파일 (세션 기반 가정)

import http from "./http"; // 공통 axios 인스턴스( baseURL, withCredentials 등 ) 사용

/**
 * 백엔드에 "내 세션 살아있어?" 확인하는 API
 * - 로그인 상태면 200 + 사용자 정보
 * - 로그인 안 되어있으면 401
 */
export function fetchMe() {
  return http.get("/api/auth/me"); // 로그인 상태 확인/내 정보 조회
}

/**
 * 로그인 API
 * - 성공: 200(또는 204) + 세션 생성(쿠키 내려옴)
 * - 실패: 401
 */
export function login(loginId, password) {
  var body = {
    loginId: loginId,   // 사용자 입력 아이디
    password: password  // 사용자 입력 비밀번호
  };

  console.log(body);

  return http.post("/api/auth/login", body); // 로그인 요청
}

/**
 * 로그아웃 API
 * - 성공: 200(또는 204) + 서버 세션 삭제
 */
export function logout() {
  return http.post("/api/auth/logout"); // 로그아웃 요청
}