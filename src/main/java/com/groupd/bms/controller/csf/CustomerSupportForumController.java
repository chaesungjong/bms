package com.groupd.bms.controller.csf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupd.bms.controller.BaseController;
import com.groupd.bms.service.BoardService;
import com.groupd.bms.service.EnterpriseService;
import com.groupd.bms.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * CustomerSupportForumController
 * 고객지원 게시판 컨트롤러
 */
@Controller
@RequestMapping("csf")
public class CustomerSupportForumController extends BaseController{

    
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BoardService boardService;

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {

        switch(path){

            // 업체 관리
            case "business_management":{

            } break;

            default:
            	
                break;       
        }    	

        return "csf/" + path;
    }

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/add_business_management")
    public String add_business_management(HttpServletRequest request, Model model) {

        HashMap<String, Object> registrationMap = setRequest(request);
        String userId     = StringUtil.objectToString(registrationMap.get("userId"));                   //사용자ID
        String searchVal  = StringUtil.objectToString(registrationMap.get("searchVal"));                //검색어

        if(!"".equals(searchVal)){

            HashMap<String, Object> siteInfoMap = (HashMap<String, Object>)boardService.mng("SITE_DETAIL", userId, "", "", "", "", "", searchVal, "");

            if (siteInfoMap != null && siteInfoMap.size() > 0) {
    
                
                siteInfoMap.put("siteDomainExpdt", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("siteDomainExpdt"))));
                siteInfoMap.put("siteHostingExpdt", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("siteHostingExpdt"))));
                siteInfoMap.put("contractSdate", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("contractSdate"))));
                siteInfoMap.put("contractEdate", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("contractEdate"))));
                
                siteInfoMap.put("imgBusinessRegNoText", StringUtil.objectToString(siteInfoMap.get("imgBusinessRegNo")));
                siteInfoMap.put("imgDoctorLicenseText", StringUtil.objectToString(siteInfoMap.get("imgDoctorLicense")));
                siteInfoMap.put("imgDegreeCertificateText", StringUtil.objectToString(siteInfoMap.get("imgDegreeCertificate")));
                siteInfoMap.put("imgDesignAssetsText", StringUtil.objectToString(siteInfoMap.get("imgDesignAssets")));
                siteInfoMap.put("imgOpenCertificateText", StringUtil.objectToString(siteInfoMap.get("imgOpenCertificate")));
                siteInfoMap.put("imgSpecialistLicenseText", StringUtil.objectToString(siteInfoMap.get("imgSpecialistLicense")));
                siteInfoMap.put("imgEtcFilesText", StringUtil.objectToString(siteInfoMap.get("imgEtcFiles")));
                siteInfoMap.put("imgEtcText", StringUtil.objectToString(siteInfoMap.get("imgEtc")));
                
                siteInfoMap.put("regUserkey", StringUtil.objectToString(siteInfoMap.get("regUserkey")));
                siteInfoMap.put("regdate", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("regdate"))));
                siteInfoMap.put("systemtime", StringUtil.dataformat(StringUtil.objectToString(siteInfoMap.get("systemtime"))));
            
    
                 // 데이터 가져오기
                List<Map<String, Object>> snsList = boardService.mngList("siteSnsInfo_List", userId, "0", "0", "", "", "", StringUtil.objectToString(siteInfoMap.get("siteKey")), "");
    
                 ObjectMapper objectMapper = new ObjectMapper();
                 String snsListJson ="";
                try {
                     snsListJson = objectMapper.writeValueAsString(snsList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
    
                model.addAttribute("siteInfo", siteInfoMap);
                model.addAttribute("snsList", snsListJson);
                model.addAttribute("corrections", "Y");
            }
        }

        /*
         * 업체 상태 코드를 가져온다.
         */
        List<Map<String, Object>> reList = enterpriseService.codeMgtViewSiteState("LIST", "siteState","", "");
        model.addAttribute("siteStateList", reList);

        /*
         * 업체 상태 레벨 코드를 가져온다.
         */
        List<Map<String, Object>> siteStateLevelList = enterpriseService.codeMgtViewSiteState("LIST", "siteStateLevel","", "");
        model.addAttribute("siteStateLevelList", siteStateLevelList);

        /*
         * 업체 개원 상태 코드를 가져온다.
         */
        List<Map<String, Object>> siteOpenStateList = enterpriseService.codeMgtViewSiteState("LIST", "siteOpenState","", "");
        model.addAttribute("siteOpenStateList", siteOpenStateList);

        /**
         * 계약 상태 코드를 가져온다.
         */
        List<Map<String, Object>> contractreList = enterpriseService.codeMgtViewSiteState("LIST","contractState","", "");
        model.addAttribute("contractStateList", contractreList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        List<Map<String, Object>> contractPlanPDesignList = enterpriseService.codeMgtViewSiteState("LIST","contractPlanPDesign","", "");
        model.addAttribute("contractPlans", contractPlanPDesignList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        List<Map<String, Object>> contractPlanVideoList = enterpriseService.codeMgtViewSiteState("LIST","contractPlanVideo","", "");
        model.addAttribute("contractPlanVideo", contractPlanVideoList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState("LIST","snsType","", "");
        model.addAttribute("snsTypeList", snsTypeList);

        return "csf/add_business_management";
    }


    /*
     * 업체 리스트 가져오기 
     */
    @RequestMapping(value = "/customer_list", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> customerList( HttpServletRequest request) {

        HashMap<String, Object> registrationMap = setRequest(request);
        HashMap<String, Object> resMap = new HashMap<String, Object>();

        String startDate  = StringUtil.objectToString(registrationMap.get("fr_date"));                  //시작일
        String EndDate    = StringUtil.objectToString(registrationMap.get("to_date"));                  //종료일
        String searchType = StringUtil.objectToString(registrationMap.get("searchType"));               //검색타입     
        String search     = StringUtil.objectToString(registrationMap.get("search"));                   //검색어 
        String userId     = StringUtil.objectToString(registrationMap.get("userId"));                   //사용자ID


        // DataTables 파라미터 가져오기
        // DataTables 파라미터 가져오기
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));

        // 페이지 번호 계산
        int page = start / length + 1;
        // 전체 레코드 수 가져오기
        int totalRecords = Integer.parseInt(setPagination(boardService.mng("SITE_LIST_CNT", userId, String.valueOf(page), String.valueOf(length), startDate.replaceAll("-", ""), EndDate.replaceAll("-", ""), searchType, search, "")));
        // 데이터 가져오기
        List<Map<String, Object>> employeeList = boardService.mngList("SITE_LIST", userId, String.valueOf(page), String.valueOf(length), startDate, EndDate, searchType, search, "");

        resMap.put("draw", draw);
        resMap.put("recordsTotal", totalRecords);
        resMap.put("data", employeeList);

        return ResponseEntity.ok(resMap);
    }  
    
    /*
     * 거래처 상세 안내 팝업 가져오기
     */
    @RequestMapping(value = "/customer_detail", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> customerDetail(HttpServletRequest request) {

        HashMap<String, Object> requestMap = setRequest(request);
        String userId     = StringUtil.objectToString(requestMap.get("userId"));                   //사용자ID
        String searchVal  = StringUtil.objectToString(requestMap.get("searchVal"));                //검색어

        // 데이터 가져오기
        HashMap<String, Object> siteInfoMap = (HashMap<String, Object>)boardService.mng("SITE_DETAIL", userId, "", "", "", "", "", searchVal, "");

        if(siteInfoMap != null && siteInfoMap.size() > 0) {

            ObjectMapper objectMapper = new ObjectMapper();
            String snsListJson ="";

            String adress       = StringUtil.objectToString(siteInfoMap.get("address")) + " " + StringUtil.objectToString(siteInfoMap.get("addressDesc"));               // 주소
            String transaction = "Y".equals(StringUtil.objectToString(siteInfoMap.get("blogYN"))) ? "블로그" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("homepageYN"))) ? " / 홈페이지" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("reviewYN"))) ? " / 블랜딩 영상" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("instagramYN"))) ? " / 인스타그램" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("youtubeYN"))) ? " / 유튜브" : ""; 
                   transaction += "Y".equals(StringUtil.objectToString(siteInfoMap.get("momcafeYN"))) ? " / 맘카페" : ""; 
                   transaction += !"".equals(StringUtil.objectToString(siteInfoMap.get("cntBlogPos"))) ? " / 블로그 건 수(" + StringUtil.objectToString(siteInfoMap.get("cntBlogPos")) + ")건" : ""; //거래내용
            
            siteInfoMap.put("transaction", transaction);                // 거래내용
            siteInfoMap.put("adress", adress);                          // 주소

            // SNS 정보 가져오기
            List<Map<String, Object>> snsList = boardService.mngList("siteSnsInfo_List", userId, "0", "0", "", "", "", searchVal, "");

            try {
                snsListJson = objectMapper.writeValueAsString(snsList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            siteInfoMap.put("snsList", snsListJson);                     // SNS 정보
            siteInfoMap.put("retVal", "0");                       // 성공
            
             return ResponseEntity.ok(siteInfoMap);
            
        }
        
        return ResponseEntity.status(500).build();
    } 

    /**
     * 업체 등록
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> Registration(HttpServletRequest request,
                                          @RequestParam("imgBusinessRegNo") MultipartFile imgBusinessRegNo,
                                          @RequestParam("imgDoctorLicense") MultipartFile imgDoctorLicense,
                                          @RequestParam("imgDegreeCertificate") MultipartFile imgDegreeCertificate,
                                          @RequestParam("imgDesignAssets") MultipartFile imgDesignAssets,
                                          @RequestParam("imgOpenCertificate") MultipartFile imgOpenCertificate,
                                          @RequestParam("imgSpecialistLicense") MultipartFile imgSpecialistLicense,
                                          @RequestParam("imgEtcFiles") MultipartFile imgEtcFiles,
                                          @RequestParam("imgEtc") MultipartFile imgEtc ) {

        HashMap<String, Object> RegistrationMap = setRequest(request);

        // 날짜 YYYY-MM-DD -> YYYYMMDD
        RegistrationMap.put("stingExpdt", StringUtil.objectToString(RegistrationMap.get("stingExpdt")).replaceAll("-", ""));
        RegistrationMap.put("contractEdate", StringUtil.objectToString(RegistrationMap.get("contractEdate")).replaceAll("-", ""));
        RegistrationMap.put("siteHostingExpdt", StringUtil.objectToString(RegistrationMap.get("siteHostingExpdt")).replaceAll("-", ""));
        RegistrationMap.put("contractSdate", StringUtil.objectToString(RegistrationMap.get("contractSdate")).replaceAll("-", ""));
        RegistrationMap.put("siteDomainExpdt", StringUtil.objectToString(RegistrationMap.get("siteDomainExpdt")).replaceAll("-", ""));
        
        // 파일 세팅
        if(imgBusinessRegNo != null && !imgBusinessRegNo.isEmpty()) {
            RegistrationMap.put("imgBusinessRegNo", imgBusinessRegNo.getOriginalFilename());
        }

        if(imgDoctorLicense != null && !imgDoctorLicense.isEmpty()) {
            RegistrationMap.put("imgDoctorLicense", imgDoctorLicense.getOriginalFilename());
        }

        if(imgDegreeCertificate != null && !imgDegreeCertificate.isEmpty()) {
            RegistrationMap.put("imgDegreeCertificate", imgDegreeCertificate.getOriginalFilename());
        }

        if(imgDesignAssets != null && !imgDesignAssets.isEmpty()) {
            RegistrationMap.put("imgDesignAssets", imgDesignAssets.getOriginalFilename());
        }

        if(imgOpenCertificate != null && !imgOpenCertificate.isEmpty()) {
            RegistrationMap.put("imgOpenCertificate", imgOpenCertificate.getOriginalFilename());
        }

        if(imgSpecialistLicense != null && !imgSpecialistLicense.isEmpty()) {
            RegistrationMap.put("imgSpecialistLicense", imgSpecialistLicense.getOriginalFilename());
        }

        if(imgEtcFiles != null && !imgEtcFiles.isEmpty()) {
            RegistrationMap.put("imgEtcFiles", imgEtcFiles.getOriginalFilename());
        }

        if(imgEtc != null && !imgEtc.isEmpty()) {
            RegistrationMap.put("imgEtc", imgEtc.getOriginalFilename());
        }
        

        String corrections = StringUtil.objectToString(RegistrationMap.get("corrections"));

        if("Y".equals(corrections)) {
            RegistrationMap.put("gubun", "modify");
        } else {
            RegistrationMap.put("gubun", "regist");
        }

        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState("LIST","snsType","", "");
        // SMS 데이터 가공
        RegistrationMap.put("sns", getSMSData(snsTypeList, RegistrationMap));
        enterpriseService.enterpriseInsert(RegistrationMap);

        String retVal = StringUtil.objectToString(RegistrationMap.get("retVal"));

        //업체 등록 성공
        if("0".equals(retVal)) {

            // 파일 업로드
            if(imgBusinessRegNo != null && !imgBusinessRegNo.isEmpty()) uploadFileToGCS(imgBusinessRegNo, imgBusinessRegNo.getOriginalFilename());
            if(imgDoctorLicense != null && !imgDoctorLicense.isEmpty()) uploadFileToGCS(imgDoctorLicense, imgDoctorLicense.getOriginalFilename());
            if(imgDegreeCertificate != null && !imgDegreeCertificate.isEmpty()) uploadFileToGCS(imgDegreeCertificate, imgDegreeCertificate.getOriginalFilename());
            if(imgDesignAssets != null && !imgDesignAssets.isEmpty()) uploadFileToGCS(imgDesignAssets, imgDesignAssets.getOriginalFilename());
            if(imgOpenCertificate != null && !imgOpenCertificate.isEmpty()) uploadFileToGCS(imgOpenCertificate, imgOpenCertificate.getOriginalFilename());
            if(imgSpecialistLicense != null && !imgSpecialistLicense.isEmpty()) uploadFileToGCS(imgSpecialistLicense, imgSpecialistLicense.getOriginalFilename());
            if(imgEtcFiles != null && !imgEtcFiles.isEmpty()) uploadFileToGCS(imgEtcFiles, imgEtcFiles.getOriginalFilename());
            if(imgEtc != null && !imgEtc.isEmpty()) uploadFileToGCS(imgEtc, imgEtc.getOriginalFilename());

        }

        if (RegistrationMap != null)
            return ResponseEntity.ok(RegistrationMap);
        else
            return ResponseEntity.status(500).build();

    }

    private JSONObject getSMSData(List<Map<String, Object>> a, HashMap<String, Object> b) {
        String snsTypeListStr = a.toString();
        String registrationMapStr = b.toString();

        // Convert snsTypeListStr to array of maps
        snsTypeListStr = snsTypeListStr.replace("[", "").replace("]", "").replace(", ", ",");
        String[] snsTypeListArray = snsTypeListStr.split("},");
        Map<String, String> codeMap = new HashMap<>();
        for (String snsType : snsTypeListArray) {
            snsType = snsType.replace("{", "").replace("}", "");
            String[] keyValuePairs = snsType.split(",");
            Map<String, String> snsTypeMap = new HashMap<>();
            for (String pair : keyValuePairs) {
                String[] entry = pair.split("=");
                if (entry.length == 2) { // 배열 길이가 2인지 확인
                    snsTypeMap.put(entry[0].trim(), entry[1].trim()); // 공백 제거
                }
            }
            codeMap.put(snsTypeMap.get("code"), snsTypeMap.get("codeName"));
        }

        // Convert registrationMapStr to map
        registrationMapStr = registrationMapStr.replace("{", "").replace("}", "");
        String[] registrationEntries = registrationMapStr.split(",");
        Map<String, String> registrationMap = new HashMap<>();
        for (String entry : registrationEntries) {
            String[] keyValue = entry.split("=");
            if (keyValue.length == 2) { // 배열 길이가 2인지 확인
                registrationMap.put(keyValue[0].trim(), keyValue[1].trim()); // 공백 제거
            }
        }

        // Create JSON based on codeMap
        JSONObject resultJson = new JSONObject();
        for (String code : codeMap.keySet()) {
            JSONObject codeJson = new JSONObject();
            for (String key : registrationMap.keySet()) {
                if (key.startsWith(code)) {
                    codeJson.put(key, StringUtil.objectToString(registrationMap.get(key)));
                }
            }
            resultJson.put(code, codeJson);
        }

        // Print the result JSON
        return resultJson;
    }

}