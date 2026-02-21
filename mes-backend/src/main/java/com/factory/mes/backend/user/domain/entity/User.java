package com.factory.mes.backend.user.domain.entity;

import java.util.Date;

public class User {
	  	private Long userSeq;          // USER_SEQ 식별번호
	    private String loginId;        // LOGIN_ID 로그인 아이디
	    private String userNm;         // USER_NM 이름

	    private String passwordHash;   // PASSWORD_HASH 비밀번호 해시 (*&(*&(

	    private String email;          // EMAIL 사용자 이메일
	    private String phoneNo;        // PHONE_NO 사용자 폰번호

	    private String deptCd;         // DEPT_CD 부서
	    private String roleCd;         // ROLE_CD 권한

	    private String statusCd;       // STATUS_CD 퇴사 여부
	    private Integer failCnt;       // FAIL_CNT 로그인 실패 횟수
	    private String lockYn;         // LOCK_YN 아이디 잠김 여부 아마 로그인 실패 횟수 몇번 이상하면 잠기게 하는거
	    private Date lastLoginDt;      // LAST_LOGIN_DT 마지막 로그인일자

	    private String useYn;          // USE_YN 아이디 사용 여부를 Y/N으로 표시 정보를 날리는게 아니라 UPDATE로 변경

	    private String createdBy;      // CREATED_BY 이 사용자를 등록한 사람
	    private Date createdDt;        // CREATED_DT 가입일
	    private String updatedBy;      // UPDATED_BY 마지막으로 사용자 정보를 수정한 사람
	    private Date updatedDt;        // UPDATED_DT 정보 변경된 시간

	    // getter/setter (필요하면 Lombok으로 대체 가능)
	    public Long getUserSeq() { return userSeq; }
	    public void setUserSeq(Long userSeq) { this.userSeq = userSeq; }

	    public String getLoginId() { return loginId; }
	    public void setLoginId(String loginId) { this.loginId = loginId; }

	    public String getUserNm() { return userNm; }
	    public void setUserNm(String userNm) { this.userNm = userNm; }

	    public String getPasswordHash() { return passwordHash; }
	    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPhoneNo() { return phoneNo; }
	    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

	    public String getDeptCd() { return deptCd; }
	    public void setDeptCd(String deptCd) { this.deptCd = deptCd; }

	    public String getRoleCd() { return roleCd; }
	    public void setRoleCd(String roleCd) { this.roleCd = roleCd; }

	    public String getStatusCd() { return statusCd; }
	    public void setStatusCd(String statusCd) { this.statusCd = statusCd; }

	    public Integer getFailCnt() { return failCnt; }
	    public void setFailCnt(Integer failCnt) { this.failCnt = failCnt; }

	    public String getLockYn() { return lockYn; }
	    public void setLockYn(String lockYn) { this.lockYn = lockYn; }

	    public Date getLastLoginDt() { return lastLoginDt; }
	    public void setLastLoginDt(Date lastLoginDt) { this.lastLoginDt = lastLoginDt; }

	    public String getUseYn() { return useYn; }
	    public void setUseYn(String useYn) { this.useYn = useYn; }

	    public String getCreatedBy() { return createdBy; }
	    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

	    public Date getCreatedDt() { return createdDt; }
	    public void setCreatedDt(Date createdDt) { this.createdDt = createdDt; }

	    public String getUpdatedBy() { return updatedBy; }
	    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

	    public Date getUpdatedDt() { return updatedDt; }
	    public void setUpdatedDt(Date updatedDt) { this.updatedDt = updatedDt; }
}
