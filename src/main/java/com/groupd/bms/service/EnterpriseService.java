package com.groupd.bms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.repository.EnterpriseRepository;
import com.groupd.bms.util.StringUtil;

import org.json.JSONObject;

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
     * 
     * @param requestHashMap
     */
    public void enterpriseInsert(HashMap<String, Object> requestHashMap) {

        enterpriseRepository.siteInfoModify(requestHashMap);

        String retVal = StringUtil.objectToString(requestHashMap.get("retVal"));
                                 

        if ("0".equals(retVal)) {
            // 성공
            String sitekey = StringUtil.objectToString(requestHashMap.get("retSiteKey"));
            String userid = (String) requestHashMap.get("userId");
            JSONObject jsonObject = (JSONObject) requestHashMap.get("sns");
            // 필요한 정보 출력
            jsonObject.keys().forEachRemaining(key -> {

                JSONObject snsObject = jsonObject.getJSONObject(key);
                String siteDomain = snsObject.optString(key + "siteDomain");
                String id = snsObject.optString(key + "id");
                String pw = snsObject.optString(key + "pw");

                HashMap<String, Object> siteSnsInfoMap = new HashMap<>();
                siteSnsInfoMap.put("svrGubun", "siteSnsInfo");
                siteSnsInfoMap.put("gubun", "REGIST");
                siteSnsInfoMap.put("loginUserid", userid);
                siteSnsInfoMap.put("loginUserip", "testIp");
                siteSnsInfoMap.put("i_param1", sitekey);
                siteSnsInfoMap.put("i_param2", key);
                siteSnsInfoMap.put("i_param3", siteDomain);
                siteSnsInfoMap.put("i_param4", id);
                siteSnsInfoMap.put("i_param5", pw);
                siteSnsInfoMap.put("i_param6", "");
                siteSnsInfoMap.put("i_param7", "");
                siteSnsInfoMap.put("i_param8", "");
                siteSnsInfoMap.put("i_param9", "");

                enterpriseRepository.mngRegist(siteSnsInfoMap);
            });

        } else {

        }

    }

    /**
     * 업체 코드관리를 한다.
     */
    public List<Map<String, Object>> codeMgtViewSiteState(String gubun, String codeGubun, String code, String codeName) {

        HashMap<String, Object> getStateCode = new HashMap<>();

        getStateCode.put("gubun", gubun);
        getStateCode.put("codeGubun", codeGubun);
        getStateCode.put("code", code);
        getStateCode.put("codeName", codeName);

        List<Map<String, Object>> reList = enterpriseRepository.codeMgtViewSiteState(getStateCode);
        return reList;
    }
}
