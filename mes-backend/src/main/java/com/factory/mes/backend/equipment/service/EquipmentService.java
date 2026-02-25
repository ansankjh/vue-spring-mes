package com.factory.mes.backend.equipment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.factory.mes.backend.equipment.domain.dto.EquipmentListItemResponse;
import com.factory.mes.backend.equipment.mapper.EquipmentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EquipmentService {
	
	private final EquipmentMapper equipmentMapper;
	
	// 생성자
	public EquipmentService(EquipmentMapper equipmentMapper) { // 생성자 주입
		this.equipmentMapper = equipmentMapper; // 주입받은 mapper 저장
	}
	
	/**
     * 설비 리스트 + 현재상태 조회
     * @param lineCd 라인 필터(선택)
     * @param processCd 공정 필터(선택)
     * @param keyword 설비코드/명 검색(선택)
     */
	public List<EquipmentListItemResponse> getEquipmentList(String lineCd, String processCd, String keyword) {
		List<EquipmentListItemResponse> list = new ArrayList<EquipmentListItemResponse>();
		
		list = equipmentMapper.selectEquipmentList(lineCd, processCd, keyword);
		
		log.debug("EquipmentService List Debug : " + list);
		
		// Mapper 호출해서 설비 리스트+현재 상태를 가져옴
		return list;
	}
}
