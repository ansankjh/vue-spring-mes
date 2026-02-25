package com.factory.mes.backend.equipment.domain.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MES_EQUIP_STATUS {
	// 설비 현재상태
	private Long equipSeq; // FK(MESEQUIPMENT.EQUIP_SEQ)
	private String statusCd; // RUN/IDLE/DOWN/PM
	private String reasonCd; // 다운/유휴 사유코드
	private Date lastEventDt; // 상태가 바뀐시각
	private Date heartbeatDt; //설비 통신/수집 최종시각
	private String commentTxt; // 비고
	
	private String updatedBy;
	private Date updateDt;
}
