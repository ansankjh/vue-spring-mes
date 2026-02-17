package com.factory.mes.backend.user.mapper;

import com.factory.mes.backend.user.domain.entity.User; // 조회 결과를 User로 받기 위해 import
import org.apache.ibatis.annotations.Mapper;           // MyBatis Mapper로 등록하기 위해 import
import org.apache.ibatis.annotations.Param;            // 파라미터 이름을 XML에서 쓰기 위해 import

@Mapper // MyBatis가 이 인터페이스를 Mapper로 인식하게 함
public interface UserMapper {
	
	 /**
     * loginId로 사용자 1건 조회
     * - 다음 단계에서 UserMapper.xml에 동일 id(selectByLoginId)로 SQL을 작성해야 함
     */
    User selectByLoginId(@Param("loginId") String loginId);
}