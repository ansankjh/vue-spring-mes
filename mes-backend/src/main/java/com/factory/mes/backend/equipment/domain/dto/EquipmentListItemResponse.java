package com.factory.mes.backend.equipment.domain.dto;

import lombok.Data;
import java.util.Date;

/**
 * 설비 리스트 1건(현재상태 포함) 응답 DTO
 */
@Data
public class EquipmentListItemResponse {

    // MES_EQUIPMENT 모든값 반환
    private Long equipSeq;      // 내부 PK
    private String equipCd;     // 설비코드
    private String equipNm;     // 설비명
    private String lineCd;      // 라인
    private String processCd;   // 공정
    private String areaCd;      // 구역
    private String useYn;       // 사용여부(Y/N)

    // MES_EQUIP_STATUS (LEFT JOIN) 일치하는 값만 반환
    private String statusCd;    // RUN/IDLE/DOWN/PM
    private String reasonCd;    // 사유코드(선택)
    private Date lastEventDt;   // 상태 변경 시각(선택)
    private Date heartbeatDt;   // 수집/통신 최종시각(선택)
}