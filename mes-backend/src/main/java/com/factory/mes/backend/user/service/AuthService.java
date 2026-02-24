package com.factory.mes.backend.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;                      // 스프링이 이 클래스를 Service 빈으로 등록하게 하기 위해 import
import org.springframework.web.server.ResponseStatusException;

import com.factory.mes.backend.user.domain.dto.LoginRequest;
import com.factory.mes.backend.user.domain.dto.MeResponse;          // /me 응답 DTO를 반환하기 위해 import
import com.factory.mes.backend.user.domain.entity.User;             // DB에서 조회한 User 엔티티를 받기 위해 import
import com.factory.mes.backend.user.mapper.UserMapper;              // MyBatis Mapper를 통해 DB 조회를 하기 위해 import

import lombok.extern.slf4j.Slf4j;


/**
 * 인증/세션 기반 사용자 조회 관련 서비스
 * - AuthController는 "세션에서 loginId 꺼내기 + 응답"만 담당
 * - 실제 DB 조회/정책 필터링(useYn, status, lock 등)은 여기서 담당
 */
@Slf4j
@Service // 스프링이 이 클래스를 서비스 빈으로 등록해서 주입 가능하게 함
public class AuthService {

	private final UserMapper userMapper; // 사용자 조회 쿼리를 실행할 Mapper
	private final BCryptPasswordEncoder passwordEncoder;
	
	public AuthService(UserMapper userMapper) { // 스프링이 UserMapper 빈을 주입해 주도록 생성자 선언
        this.userMapper = userMapper;           // 주입받은 mapper를 필드에 저장(이후 메소드에서 사용)
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
	
	public MeResponse login(LoginRequest req) {
		// LoginRequest DTO에 있는 loginId를 LoginRequset req에서 꺼내서 사용
		User loginUser = userMapper.selectUserForLogin(req.getLoginId());
		
		// 사용자 조회해서 없으면 아래 HTTP 응답
		if(loginUser == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		// 계정 사용 여부/상태 체크
		// 예 USE_YN, STATUS_CD, LOCK_YN등
		if("N".equals(loginUser.getUseYn())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용할 수 없는 계정입니다.");
		}
		
		// 비밀번호 검증
		boolean pwCheck = passwordEncoder.matches(req.getPassword(), loginUser.getPasswordHash());
		if (!pwCheck) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		// 로그인 성공시
		MeResponse me = new MeResponse();
		me.setUserSeq(loginUser.getUserSeq());
		me.setLoginId(loginUser.getLoginId());
		me.setUserNm(loginUser.getUserNm());
		me.setRoleCd(loginUser.getRoleCd());
		me.setDeptCd(loginUser.getDeptCd());
		
		return me;
	}
	
	/**
     * /api/auth/me 용: loginId로 현재 사용자 정보 조회
     * - 없으면 null 반환(컨트롤러에서 401 처리)
     */
	public MeResponse getUserContext(int userSeq) {
		// 1) loginId로 사용자 엔티티 조회(다음 단계에서 UserMapper에 메소드 만들 예정)
		User user = userMapper.selectUserAuthDetails(userSeq);
		
		// 2) 조회 결과가 없으면(null) 그대로 null 반환 -> 컨트롤러가 401 처리
		if (user == null) {
			return null;
		}
		
		// USE_YN = 'Y'만 허용하고 싶으면 여기서 필터링 가능
		// DB 쿼리에서 WHERE USE_YN='Y' 걸 수도 있고, 여기서 걸수도 있음(쿼리에서 거는게 효율적이라 쿼리에서 걸기로 결정)
		
		// 엔티티(User) -> DTO(MeResponse)로 변환(필요한 것만 복사)
		MeResponse res = new MeResponse();
		res.setUserSeq(user.getUserSeq());
        res.setLoginId(user.getLoginId());
        res.setUserNm(user.getUserNm());
        res.setDeptCd(user.getDeptCd());
        res.setRoleCd(user.getRoleCd());
        res.setStatusCd(user.getStatusCd());
        res.setLockYn(user.getLockYn());
        res.setUseYn(user.getUseYn());
		
		return res;
	}
}
