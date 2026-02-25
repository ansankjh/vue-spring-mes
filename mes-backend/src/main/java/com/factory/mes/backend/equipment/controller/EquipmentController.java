package com.factory.mes.backend.equipment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.factory.mes.backend.equipment.domain.dto.EquipmentListItemResponse;
import com.factory.mes.backend.equipment.service.EquipmentService;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
	
	// EquipmentService 선언
	private final EquipmentService equipmentService;
	
	// 생성자 주입
	public EquipmentController(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}
	
	@GetMapping
	public List<EquipmentListItemResponse> getEquipmentList(
			@RequestParam(name = "lineCd", required = false) String lineCd,
			@RequestParam(name = "processCd", required = false) String processCd,
			@RequestParam(name = "keyword", required = false) String keyword) {
		return equipmentService.getEquipmentList(lineCd, processCd, keyword);
	}
}
