package com.factory.mes.backend.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.factory.mes.backend.equipment.domain.dto.EquipmentListItemResponse;

/**
 * 설비(대시보드) 조회용 MyBatis Mapper
 */
@Mapper
public interface EquipmentMapper {
	/**
     * 설비 리스트 + 현재상태 조회
     * @param lineCd 라인 필터(선택)
     * @param processCd 공정 필터(선택)
     * @param keyword 설비코드/명 검색(선택)
     */
	List<EquipmentListItemResponse> selectEquipmentList(
			@Param("lineCd") String lineCd,
			@Param("processCd") String processCd,
			@Param("keyword") String keyword
	);
}
