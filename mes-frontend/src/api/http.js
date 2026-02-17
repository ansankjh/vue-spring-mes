import axios from "axios";

/**
 * 세션 기반이면 브라우저 쿠키(JSESSIONID)가 인증수단이라
 * 요청할 때 쿠키가 반드시 포함되어야 함.
 */
var http = axios.create({
  baseURL: "http://localhost:8081", // 백엔드 주소(네 환경에 맞게)
  withCredentials: true,            // ★ 쿠키 포함해서 요청 보내기
  timeout: 10000,
  headers: {
    "Content-Type": "application/json"
  }
});

export default http;