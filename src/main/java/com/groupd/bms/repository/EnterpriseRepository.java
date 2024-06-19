package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseRepository {

    /**
     * 거래처 정보 등록/수정
     * 
     * @return
     */
    void siteInfoModify(Map<String, Object> params);
    
    /**
     * 거래처 정보 등록/수정
     * 
     * @return
     */
    void mngRegist(Map<String, Object> params);

    /**
     * 거래처 코드관리
     * 
     * @return
     */
    List<Map<String, Object>> codeMgtViewSiteState(Map<String, Object> params);

}
