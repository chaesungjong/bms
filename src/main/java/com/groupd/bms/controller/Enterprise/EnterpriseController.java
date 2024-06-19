package com.groupd.bms.controller.Enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.groupd.bms.model.Member;
import com.groupd.bms.service.EnterpriseService;

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
        HashMap<String, Object> snsType = new HashMap<>();

        snsType.put("gubun", "LIST");
        snsType.put("codeGubun", "snsType"); 
        snsType.put("code", ""); 
        snsType.put("codeName", ""); 

        List<Map<String, Object>> snsTypeList = enterpriseService.codeMgtViewSiteState(snsType);
        model.addAttribute("snsTypeList", snsTypeList);
        
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

        enterpriseService.enterpriseInsert(RegistrationMap);

        if (RegistrationMap != null)
            return ResponseEntity.ok(RegistrationMap);
        else
            return ResponseEntity.status(500).build();

    }

}
