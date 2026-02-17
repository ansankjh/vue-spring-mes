package com.factory.mes.backend.user.controller;

import org.springframework.http.HttpStatus;			 // 200/401 같은 HTTP 상태코드를 쓰기 위해 import
import org.springframework.http.ResponseEntity;            // 상태코드 + 바디를 함께 리턴하기 위해 import
import org.springframework.web.bind.annotation.GetMapping; // GET 요청 매핑을 위해 import
import org.springframework.web.bind.annotation.RequestMapping; // 공통 URL prefix(/api/auth) 지정을 위해 import
import org.springframework.web.bind.annotation.RestController; // JSON 응답 컨트롤러로 만들기 위해 import

import com.factory.mes.backend.user.domain.dto.MeResponse; // /me 응답 DTO를 쓰기 위해 import
import com.factory.mes.backend.user.service.AuthService; // 인증 관련 비즈니스 로직(조회 등)을 위임하기 위해 import

import jakarta.servlet.http.HttpSession; // 세션 기반 로그인 상태를 확인/조회하기 위해 import

@RestController // 이 클래스의 반환값을 JSON으로 응답하기 위해 사용(@ResponseBody 포함)
@RequestMapping("/api/auth") // 이 컨트롤러의 모든 API는 /api/auth 로 시작하게 함
public class AuthController {
	
	private static final String SESSION_LOGIN_ID = "LOGIN_ID"; // 세션에 로그인 아이디를 저장/조회할 때 쓰는 키 이름

    private final AuthService authService; // 실제 사용자 정보 조회 로직은 서비스로 위임하기 위해 선언

    public AuthController(AuthService authService) { // 스프링이 AuthService를 주입하도록 생성자 구성
        this.authService = authService; // 주입받은 서비스를 필드에 보관
    }
    
    @GetMapping("/me") // Vue에서 GET으로 보낸 /api/auth/me 요청 처리 
    public ResponseEntity<?> me(HttpSession session) { // HttpSession으로 로그인 여부를 확인하기 위해 파라미터로 받음
    	Object loginIdObj = session.getAttribute(SESSION_LOGIN_ID); // 세션에서 로그인 아이디를 꺼냄(로그인 시 저장해둔 값)
    	
    	if(loginIdObj == null) { // 세션에 값이 없으면 로그인 안한 상태로 판단 
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 반환(프론트에서 로그인 화면으로 보내기 쉬움) Vue 메소드에서 401 에러 받는 로직이 있는데 /login 화면으로 이동하는걸 말함
    	}
    	
    	String loginId = String.valueOf(loginIdObj); // 세션 값(Object)을 문자열 LoginId로 변환
    	MeResponse me = authService.getMe(loginId); // 서비스에 "LoginId의 내 정보" 조회를 위임 걍 authService에 있는 getMe 호출
    	
    	if(me == null) { // DB에 사용자가 없거나(useYn=N 등) 정책상 조회 불가일 수 있음
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 동일하게 401로 처리(세션은 있지만 유효하지 않은 사용자)
    	}
    	
    	return ResponseEntity.ok(me); // 200 + MeResponse JSON 응답 반환
    }

}
