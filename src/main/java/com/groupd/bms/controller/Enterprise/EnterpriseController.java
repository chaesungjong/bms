package com.groupd.bms.controller.Enterprise;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.groupd.bms.model.Member;
import com.groupd.bms.service.EnterpriseService;
import com.groupd.bms.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

/*
 * EnterpriseController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /*
     * 치과 업체 등록 페이지를 보여준다.
     */
    @GetMapping("/registration")
    public String Registration(Model model) {

        /*
         * 업체 상태 코드를 가져온다.
         */
        HashMap<String, Object> getStateCode = new HashMap<>();

        getStateCode.put("gubun", "LIST");
        getStateCode.put("codeGubun", "siteState");
        getStateCode.put("code", "");
        getStateCode.put("codeName", "");

        List<Map<String, Object>> reList = enterpriseService.codeMgtViewSiteState(getStateCode);

        model.addAttribute("siteState", reList);

        /**
         * 계약 상태 코드를 가져온다.
         */
        HashMap<String, Object> contractState = new HashMap<>();
        contractState.put("gubun", "LIST");
        contractState.put("codeGubun", "contractState");
        contractState.put("code", "");
        contractState.put("codeName", "");

        List<Map<String, Object>> contractreList = enterpriseService.codeMgtViewSiteState(contractState);
        model.addAttribute("contractState", contractreList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanPDesign = new HashMap<>();

        contractPlanPDesign.put("gubun", "LIST");
        contractPlanPDesign.put("codeGubun", "contractPlanPDesign");
        contractPlanPDesign.put("code", "");
        contractPlanPDesign.put("codeName", "");

        List<Map<String, Object>> contractPlanPDesignList = enterpriseService.codeMgtViewSiteState(contractPlanPDesign);
        model.addAttribute("contractPlans", contractPlanPDesignList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanVideo = new HashMap<>();

        contractPlanVideo.put("gubun", "LIST");
        contractPlanVideo.put("codeGubun", "contractPlanVideo");
        contractPlanVideo.put("code", "");
        contractPlanVideo.put("codeName", "");

        List<Map<String, Object>> contractPlanVideoList = enterpriseService.codeMgtViewSiteState(contractPlanVideo);
        model.addAttribute("contractPlanVideo", contractPlanVideoList);

        /**
         * SNS 상태 값을 가져온다.
         */
        // HashMap<String, Object> snsType = new HashMap<>();

        // snsType.put("gubun", "LIST");
        // snsType.put("codeGubun", "snsType");
        // snsType.put("code", "");
        // snsType.put("codeName", "");

        // List<Map<String, Object>> snsTypeList =
        // enterpriseService.codeMgtViewSiteState(snsType);
        // model.addAttribute("snsTypeList", snsTypeList);

        return "enterprise/Registration";
    }

    /*
     * 대시보드 페이지를 보여준다.
     */
    @GetMapping("/main")
    public String main(Model model) {

        /*
         * 업체 상태 코드를 가져온다.
         */
        HashMap<String, Object> getStateCode = new HashMap<>();

        getStateCode.put("gubun", "LIST");
        getStateCode.put("codeGubun", "siteState");
        getStateCode.put("code", "");
        getStateCode.put("codeName", "");

        List<Map<String, Object>> reList = enterpriseService.codeMgtViewSiteState(getStateCode);

        model.addAttribute("siteState", reList);

        /**
         * 계약 상태 코드를 가져온다.
         */
        HashMap<String, Object> contractState = new HashMap<>();
        contractState.put("gubun", "LIST");
        contractState.put("codeGubun", "contractState");
        contractState.put("code", "");
        contractState.put("codeName", "");

        List<Map<String, Object>> contractreList = enterpriseService.codeMgtViewSiteState(contractState);
        model.addAttribute("contractState", contractreList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanPDesign = new HashMap<>();

        contractPlanPDesign.put("gubun", "LIST");
        contractPlanPDesign.put("codeGubun", "contractPlanPDesign");
        contractPlanPDesign.put("code", "");
        contractPlanPDesign.put("codeName", "");

        List<Map<String, Object>> contractPlanPDesignList = enterpriseService.codeMgtViewSiteState(contractPlanPDesign);
        model.addAttribute("contractPlans", contractPlanPDesignList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanVideo = new HashMap<>();

        contractPlanVideo.put("gubun", "LIST");
        contractPlanVideo.put("codeGubun", "contractPlanVideo");
        contractPlanVideo.put("code", "");
        contractPlanVideo.put("codeName", "");

        List<Map<String, Object>> contractPlanVideoList = enterpriseService.codeMgtViewSiteState(contractPlanVideo);
        model.addAttribute("contractPlanVideo", contractPlanVideoList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> snsType = new HashMap<>();

        snsType.put("gubun", "LIST");
        snsType.put("codeGubun", "snsType");
        snsType.put("code", "");
        snsType.put("codeName", "");

        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState(snsType);
        model.addAttribute("snsTypeList", snsTypeList);

        return "enterprise/main";
    }

    /*
     * 대시보드 페이지를 보여준다.
     */
    @GetMapping("/mainback")
    public String mainback(Model model) {

        /*
         * 업체 상태 코드를 가져온다.
         */
        HashMap<String, Object> getStateCode = new HashMap<>();

        getStateCode.put("gubun", "LIST");
        getStateCode.put("codeGubun", "siteState");
        getStateCode.put("code", "");
        getStateCode.put("codeName", "");

        List<Map<String, Object>> reList = enterpriseService.codeMgtViewSiteState(getStateCode);

        model.addAttribute("siteState", reList);

        /**
         * 계약 상태 코드를 가져온다.
         */
        HashMap<String, Object> contractState = new HashMap<>();
        contractState.put("gubun", "LIST");
        contractState.put("codeGubun", "contractState");
        contractState.put("code", "");
        contractState.put("codeName", "");

        List<Map<String, Object>> contractreList = enterpriseService.codeMgtViewSiteState(contractState);
        model.addAttribute("contractState", contractreList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanPDesign = new HashMap<>();

        contractPlanPDesign.put("gubun", "LIST");
        contractPlanPDesign.put("codeGubun", "contractPlanPDesign");
        contractPlanPDesign.put("code", "");
        contractPlanPDesign.put("codeName", "");

        List<Map<String, Object>> contractPlanPDesignList = enterpriseService.codeMgtViewSiteState(contractPlanPDesign);
        model.addAttribute("contractPlans", contractPlanPDesignList);

        /**
         * 계약 계약플랜(포스팅/디자인)을 가져온다.
         */
        HashMap<String, Object> contractPlanVideo = new HashMap<>();

        contractPlanVideo.put("gubun", "LIST");
        contractPlanVideo.put("codeGubun", "contractPlanVideo");
        contractPlanVideo.put("code", "");
        contractPlanVideo.put("codeName", "");

        List<Map<String, Object>> contractPlanVideoList = enterpriseService.codeMgtViewSiteState(contractPlanVideo);
        model.addAttribute("contractPlanVideo", contractPlanVideoList);

        /**
         * SNS 상태 값을 가져온다.
         */
        HashMap<String, Object> snsType = new HashMap<>();

        snsType.put("gubun", "LIST");
        snsType.put("codeGubun", "snsType");
        snsType.put("code", "");
        snsType.put("codeName", "");

        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState(snsType);
        model.addAttribute("snsTypeList", snsTypeList);

        return "enterprise/mainback";
    }

    /*
     * 업체 등록 페이지를 보여준다.
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> Registration(HttpServletRequest request) {

        // 로그인 시도
        HashMap<String, Object> RegistrationMap = new HashMap<>();
        Member member = (Member) request.getSession().getAttribute("member");

        RegistrationMap.put("gubun", "regist");
        RegistrationMap.put("userid", member.getUserid());
        RegistrationMap.put("siteClass", "DT");

        request.getParameterMap().forEach((key, value) -> {
            RegistrationMap.put(key, value[0]);
        });

        /**
         * SNS 상태 값을 가져온다.
         */
        HashMap<String, Object> snsType = new HashMap<>();

        snsType.put("gubun", "LIST");
        snsType.put("codeGubun", "snsType");
        snsType.put("code", "");
        snsType.put("codeName", "");

        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState(snsType);

        RegistrationMap.put("sns", getSMSData(snsTypeList, RegistrationMap));
        enterpriseService.enterpriseInsert(RegistrationMap);

        if (RegistrationMap != null)
            return ResponseEntity.ok(RegistrationMap);
        else
            return ResponseEntity.status(500).build();

    }

    public JSONObject getSMSData(List<Map<String, Object>> a, HashMap<String, Object> b) {
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
