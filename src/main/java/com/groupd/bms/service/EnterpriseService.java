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
     * @param requestHashMap
     */
    public void enterpriseInsert(HashMap<String, Object> requestHashMap) {

        //업체 등록 및 수정
        enterpriseRepository.siteInfoModify(requestHashMap);

        String retVal = StringUtil.objectToString(requestHashMap.get("retVal"));
        
        // 성공
        if ("0".equals(retVal)) {
            
            //업체 계정 정보 등록
            String sitekey = StringUtil.objectToString(requestHashMap.get("retSiteKey"));
            String userid = (String) requestHashMap.get("userId");
            JSONObject jsonObject = (JSONObject) requestHashMap.get("sns");

            // 필요한 정보 출력
            jsonObject.keys().forEachRemaining(key -> {

                JSONObject snsObject = jsonObject.getJSONObject(key);
                String siteDomain = snsObject.optString(key + "siteDomain");
                String id  = snsObject.optString(key + "id");
                String pw  = snsObject.optString(key + "pw");
                String seq = StringUtil.objectToString(snsObject.optString(key + "seq"));

                HashMap<String, Object> siteSnsInfoMap = new HashMap<>();
                siteSnsInfoMap.put("svrGubun", "siteSnsInfo");

                if("".equals(seq)) {
                    siteSnsInfoMap.put("gubun", "REGIST");
                }else{
                    siteSnsInfoMap.put("gubun", "MODIFY");
                }

                siteSnsInfoMap.put("loginUserid", userid);
                siteSnsInfoMap.put("loginUserip", requestHashMap.get("ip"));
                siteSnsInfoMap.put("i_param1", sitekey);
                siteSnsInfoMap.put("i_param2", key);
                siteSnsInfoMap.put("i_param3", siteDomain);
                siteSnsInfoMap.put("i_param4", id);
                siteSnsInfoMap.put("i_param5", pw);
                siteSnsInfoMap.put("i_param6", seq);
                siteSnsInfoMap.put("i_param7", "");
                siteSnsInfoMap.put("i_param8", "");
                siteSnsInfoMap.put("i_param9", "");

                enterpriseRepository.mngRegist(siteSnsInfoMap);
            });

            // 담당자 등록 하기 
            Map<String, String> parameterMap = requestHashMap.get("bmsMember") != null ? (Map<String, String>) requestHashMap.get("bmsMember") : null;
            
            if(parameterMap != null) {

                // 담당자 한명씩 등록
                for (String key : parameterMap.keySet()) {

                    String seq = StringUtil.objectToString(parameterMap.get(key+"seq"));

                    Map<String, Object> memberReMap = new HashMap<>();

                    if("".equals(seq)) memberReMap.put("gubun", "REGIST");
                    else memberReMap.put("gubun", "MODIFY");
                    

                    memberReMap.put("svrGubun", "siteMngUserInfo");
                    memberReMap.put("loginUserid", userid);
                    memberReMap.put("loginUserip", requestHashMap.get("ip"));
                    memberReMap.put("i_param1", sitekey);
                    memberReMap.put("i_param2", (String)parameterMap.get(key));
                    memberReMap.put("i_param3","SITE_GD");
                    enterpriseRepository.mngRegist(memberReMap);

                }
            }

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
