package com.factory.mes.backend.user.domain.dto;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String loginId; // 사용자 아이디
	private String password; // 사용자 비밀번호
}
