package com.groupd.bms.controller.enterprise.dsb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.groupd.bms.controller.BaseController;
import com.groupd.bms.service.CommonService;
import com.groupd.bms.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

/*
 * EnterpriseController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("enterprise")
public class EnterpriseController extends BaseController{



    @Autowired
    private CommonService commonService;

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {
        return "enterprise/" + path;
    }

    /*
     * 업체 상세 안내 팝업 가져오기
     */
    @RequestMapping(value = "/customer_detail", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> customerDetail(HttpServletRequest request) {

        HashMap<String, Object> requestMap = setRequest(request);
        String userId     = StringUtil.objectToString(requestMap.get("userId"));                   //사용자ID
        String searchVal  = StringUtil.objectToString(requestMap.get("searchVal"));                //검색어

        // 데이터 가져오기
        HashMap<String, Object> siteInfoMap = (HashMap<String, Object>)commonService.mng("SITE_DETAIL", userId, "", "", "", "", "", searchVal, "");

        // 데이터가 있을 경우
        if(siteInfoMap != null && siteInfoMap.size() > 0) {

            // 업체 그룹디담당자 리스트
            List<Map<String, Object>> RegMemberList = commonService.mngList("siteMngUser_List", userId, "0", "0", "", "", "", StringUtil.objectToString(siteInfoMap.get("siteKey")), "");

            String contactInformation = "";

            for(int i = 0; i < RegMemberList.size(); i++) {

                contactInformation = contactInformation + StringUtil.objectToString(RegMemberList.get(i).get("name")) + StringUtil.objectToString(RegMemberList.get(i).get("jobTitle"));
                if(i == RegMemberList.size() - 1) break;
                contactInformation = contactInformation + ", ";
            }

            String adress      = StringUtil.objectToString(siteInfoMap.get("address")) + " " + StringUtil.objectToString(siteInfoMap.get("addressDesc"));                                          // 주소
            String transaction = "Y".equals(StringUtil.objectToString(siteInfoMap.get("blogYN"))) ? "블로그" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("homepageYN"))) ? " / 홈페이지" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("reviewYN"))) ? " / 블랜딩 영상" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("instagramYN"))) ? " / 인스타그램" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("youtubeYN"))) ? " / 유튜브" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("momcafeYN"))) ? " / 맘카페" : ""; 
                   transaction += !"".equals(StringUtil.objectToString(siteInfoMap.get("cntBlogPos"))) ? " / 블로그 건 수(" + StringUtil.objectToString(siteInfoMap.get("cntBlogPos")) + ")건" : ""; //거래내용
            
            siteInfoMap.put("transaction", transaction);                // 거래내용
            siteInfoMap.put("adress", adress);                          // 주소
            siteInfoMap.put("contactInformation", contactInformation);  // 연락처
            
            // SNS 정보 가져오기
            List<Map<String, Object>> snsList = commonService.mngList("siteSnsInfo_List", userId, "0", "0", "", "", "", searchVal, "");
            String snsListJson = setJsonToMap(snsList);
            siteInfoMap.put("snsList", snsListJson);                     // SNS 정보
            siteInfoMap.put("retVal", "0");                        // 성공
            return ResponseEntity.ok(siteInfoMap);
        }
        
        return ResponseEntity.status(500).build();
    }

}
