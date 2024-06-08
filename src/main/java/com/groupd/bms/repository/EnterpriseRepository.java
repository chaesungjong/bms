package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;
import java.util.HashMap;

@Mapper
public interface EnterpriseRepository {

    /**
     * 거래처 정보 등록/수정
     * 
     * @return
     */
    HashMap<String, Object> siteInfoModify(Map<String, Object> params);

}
