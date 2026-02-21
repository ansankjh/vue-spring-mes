package com.factory.mes.backend.user.domain.dto;

import java.util.Date;

import lombok.Data;

/**
 * /api/auth/me 응답 DTO
 * - "현재 로그인한 사용자"의 기본 프로필/권한 정보를 프론트에 내려주기 위한 객체
 * - 보안상 PASSWORD_HASH 같은 민감값은 포함하지 않음
 */
@Data
public class MeResponse {
	
	private Long userSeq;       // 내부 PK(사용자 식별/참조용)
    private String loginId;     // 로그인 아이디(계정)
    private String userNm;      // 사용자명(표시명)

    private String email;       // 이메일(선택)
    private String phoneNo;     // 전화번호(선택)

    private String deptCd;      // 부서코드(선택)
    private String roleCd;      // 권한코드(메뉴/기능 권한 체크용)

    private String statusCd;    // 상태(ACTIVE/INACTIVE 등)
    private String lockYn;      // 잠금여부(Y/N)
    private Date lastLoginDt;   // 마지막 로그인 일시(표시/감사용)

    private String useYn;       // 사용여부(Y/N)
}
