package com.factory.mes.backend.equipment.domain.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MES_EQUIP_STATUS_HIS {
	// 설비 상태 이력
	private Long hisSeq; // 이력 PK
	private Long equipSeq; // 설비 FK
	private String statusCd; // RUN/IDLE/DOWN/PM
	private String reasonCd; // 사유
	private Date startDt; // 상태 시작 시각
	private Date endDt; // 상태 종료 시각
	private String commentTxt; // 비고
	
	private String createdBy;
	private Date createdDt;
}
