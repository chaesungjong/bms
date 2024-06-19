package com.groupd.bms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.repository.EnterpriseRepository;

/**
 * EnterpriseService
 * 업체 정보를 관리하기 위한 서비스
 * 
 * @version 1.0
 * @since 2024.06.01
 */
@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    /**
     * 업체 정보를 등록/수정한다.
     * @param requestHashMap
     */
    public void enterpriseInsert(HashMap<String, Object> requestHashMap) {

        enterpriseRepository.siteInfoModify(requestHashMap);
    }

    /**
     * 업체 코드관리를 한다.
     * @param requestHashMap
     */
    public List<Map<String, Object>> codeMgtViewSiteState(HashMap<String, Object> requestHashMap) {
        List<Map<String, Object>> reList = enterpriseRepository.codeMgtViewSiteState(requestHashMap);
        return reList;
    }
}
