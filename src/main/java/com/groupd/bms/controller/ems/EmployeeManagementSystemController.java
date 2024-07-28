package com.groupd.bms.controller.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.groupd.bms.controller.BaseController;
import com.groupd.bms.model.Member;
import com.groupd.bms.service.BoardService;
import com.groupd.bms.service.EnterpriseService;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * EmployeeManagementSystemController
 * 사원 관리 컨트롤러
 */
@Controller
@RequestMapping("ems")
public class EmployeeManagementSystemController extends BaseController{
    
    @Autowired
    private UserService userService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BoardService boardService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementSystemController.class);

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path) {
        return "ems/" + path;
    }

    /*
     * 사원 목록 페이지 
     */
    @GetMapping("/employees")
    public String employees(HttpServletRequest request, Model model) {

            // 로그인 시도
        HashMap<String, Object> registrationMap = setRequest(request);
        String etcParam = StringUtil.objectToString(registrationMap.get("etcParam"));
        String userId = StringUtil.objectToString(registrationMap.get("userId"));
        List<Map<String, Object>> employeeList = boardService.mngList("USER_LIST", userId, "1", "10", "");
        model.addAttribute("employeeList", employeeList);

        return "ems/employees" ;
    }

    /*
     * 사원 기본정보 화면 페이지 
     */
    @GetMapping("/add_employees")
    public String add_employees(HttpServletRequest request, Model model) {

        //서명 리스트 불러오기
        List<Map<String, Object>> departNameList = enterpriseService.codeMgtViewSiteState("LIST", "departName", "","");
        model.addAttribute("departNameList", departNameList);

        //팀명 리스트 불러오기
        List<Map<String, Object>> teamNameList = enterpriseService.codeMgtViewSiteState("LIST", "teamName", "","");
        model.addAttribute("teamNameList", teamNameList);

        //직급 리스트 불러오기
        List<Map<String, Object>> jobPositionList = enterpriseService.codeMgtViewSiteState("LIST", "jobPosition", "","");
        model.addAttribute("jobPositionList", jobPositionList);

        //직책 리스트 불러오기
        List<Map<String, Object>> jobTitleList = enterpriseService.codeMgtViewSiteState("LIST", "jobTitle", "","");
        model.addAttribute("jobTitleList", jobTitleList);

        //재직 상태 리스트 불러오기
        List<Map<String, Object>> jobStatusList = enterpriseService.codeMgtViewSiteState("LIST", "jobStatus", "","");
        model.addAttribute("jobStatusList", jobStatusList);

        //채용구분 리스트 불러오기
        List<Map<String, Object>> hireTypeList = enterpriseService.codeMgtViewSiteState("LIST", "hireType", "","");
        model.addAttribute("hireTypeList", hireTypeList);

        //양/음력 구분 리스트 불러오기
        List<Map<String, Object>> calendarTypeList = enterpriseService.codeMgtViewSiteState("LIST", "calendarType", "","");
        model.addAttribute("calendarTypeList", calendarTypeList);
        
        //급여지급방법  리스트 불러오기
        List<Map<String, Object>> payGiveTypeList = enterpriseService.codeMgtViewSiteState("LIST", "payGiveType", "","");
        model.addAttribute("payGiveTypeList", payGiveTypeList);

        //결혼여부 리스트 불러오기
        List<Map<String, Object>> marriedTypeList = enterpriseService.codeMgtViewSiteState("LIST", "marriedType", "","");
        model.addAttribute("marriedTypeList", marriedTypeList);

        return "ems/add_employees" ;
    }


    /*
     * 사원 등록 처리
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> Registration( HttpServletRequest request, @RequestParam("imgBankbook") MultipartFile imgBankbook, @RequestParam("imgFamilyRL") MultipartFile imgFamilyRL,
    @RequestParam("imgProfile") MultipartFile imgProfile, @RequestParam("imgEtc") MultipartFile imgEtc) {
        

        HashMap<String, Object> RegistrationMap = setRequest(request);
        RegistrationMap.put("gubun", "MEM_JOIN");

        String imgBankbookUrl = "";
        String imgFamilyRLUrl = "";
        String imgProfileUrl = "";
        String imgEtcUrl  = "";

        
        if(imgBankbook != null && !imgBankbook.isEmpty()) {
            imgBankbookUrl = UUID.randomUUID().toString();
            RegistrationMap.put("imgBankbook", imgBankbookUrl);
        }


        if(imgFamilyRL != null && !imgFamilyRL.isEmpty()) {
            imgFamilyRLUrl = UUID.randomUUID().toString();
            RegistrationMap.put("imgFamilyRL", imgFamilyRLUrl);
        }


        if(imgProfile != null && !imgProfile.isEmpty()) {
            imgProfileUrl = UUID.randomUUID().toString();
            RegistrationMap.put("imgProfile", imgProfileUrl);
        }


        if(imgEtc != null && !imgEtc.isEmpty()) {
            imgEtcUrl = UUID.randomUUID().toString();
            RegistrationMap.put("imgEtc", imgEtcUrl);
        }
        
        //사원 등록 처리
        userService.memRegistModify(RegistrationMap);

        //사원 등록 결과
        if(RegistrationMap.get("retVal") != null) {

            String retVal = RegistrationMap.get("retVal").toString();

            //사원 등록 성공
            if("0".equals(retVal)) {
                
                if("".equals(imgBankbookUrl))    uploadFileToGCS(imgBankbook, imgBankbookUrl);
                if("".equals(imgFamilyRL))       uploadFileToGCS(imgFamilyRL, imgFamilyRLUrl);
                if("".equals(imgProfile))        uploadFileToGCS(imgProfile, imgProfileUrl);
                if("".equals(imgEtcUrl))         uploadFileToGCS(imgEtc, imgEtcUrl);
                
                return ResponseEntity.ok(RegistrationMap);
            }
            
            return ResponseEntity.ok(RegistrationMap);
        }
        
     return ResponseEntity.status(500).build();

    }

}
