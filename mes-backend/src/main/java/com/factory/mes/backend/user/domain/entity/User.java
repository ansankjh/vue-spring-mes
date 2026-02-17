package com.factory.mes.backend.user.domain.entity;

import java.util.Date;

public class User {
	  	private Long userSeq;          // USER_SEQ
	    private String loginId;        // LOGIN_ID
	    private String userNm;         // USER_NM

	    private String passwordHash;   // PASSWORD_HASH

	    private String email;          // EMAIL
	    private String phoneNo;        // PHONE_NO

	    private String deptCd;         // DEPT_CD
	    private String roleCd;         // ROLE_CD

	    private String statusCd;       // STATUS_CD
	    private Integer failCnt;       // FAIL_CNT
	    private String lockYn;         // LOCK_YN
	    private Date lastLoginDt;      // LAST_LOGIN_DT

	    private String useYn;          // USE_YN

	    private String createdBy;      // CREATED_BY
	    private Date createdDt;        // CREATED_DT
	    private String updatedBy;      // UPDATED_BY
	    private Date updatedDt;        // UPDATED_DT

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
