package com.factory.mes.backend.equipment.domain.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MES_EQUIPMENT {
	// 설비 마스터
	private Long equipSeq; // 내부 pk
	private String equipCd; // 설비코드(업무키, 중복불가)
	private String equipNm; // 설비명
	private String lineCd; // 라인 코드
	private String processCd; // 공정코드
	private String areaCd; // 구역 코드
	private String modelNm; // 모델명
	private String vendorNm; // 제조사/벤더
	private Date installDt; // 설치일
	private String userYn; // 사용여부
	private String createBy;
	private Date createDt;
	private String updatedBy;
	private Date updateDt;
}
