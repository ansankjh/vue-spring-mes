package com.factory.mes.backend.equipment.domain.dto;

import lombok.Data;
import java.util.List;

/**
 * 설비 리스트 조회 API 응답 DTO(리스트 + 페이징 정보)
 */
@Data // getter/setter, toString, equals/hashCode를 Lombok이 자동 생성
public class EquipmentListResponse {

    private List<EquipmentListItemResponse> items; // 설비 리스트(현재상태 포함)
    private int page;                              // 요청한 페이지(1부터)
    private int size;                              // 페이지당 건수
    private long totalCount;                       // 전체 건수
    private int totalPages;                        // 전체 페이지 수
}