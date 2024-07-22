package com.groupd.bms.controller.csf;

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
import com.groupd.bms.model.Member;
import com.groupd.bms.service.EnterpriseService;

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

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {

        switch(path){

            // 업체 관리
            case "business_management":{

            } break;
            // 업체 관리 -> 업체 등록
            case "add_business_management": add_business_management(model); break;

            default:
            	
                break;       
        }    	

        return "csf/" + path;
    }

    /*
     * 고객지원 게시판 -> 업체 관리
     */
    public void add_business_management(Model model) {

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
    }

    /**
     * 업체 등록
     * @param request
     * @return
     */
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> Registration(HttpServletRequest request) {

        HashMap<String, Object> RegistrationMap = setRequest(request);

        //RegistrationMap.put("sns", getSMSData(snsTypeList, RegistrationMap));
        enterpriseService.enterpriseInsert(RegistrationMap);

        if (RegistrationMap != null)
            return ResponseEntity.ok(RegistrationMap);
        else
            return ResponseEntity.status(500).build();

    }

}