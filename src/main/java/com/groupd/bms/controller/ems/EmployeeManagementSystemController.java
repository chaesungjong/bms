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
import com.groupd.bms.service.EnterpriseService;
import com.groupd.bms.service.UserService;
import com.google.cloud.storage.Storage;

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

    private final Storage storage;

    public EmployeeManagementSystemController(Storage storage) {
        this.storage = storage;
    }

    
    @Autowired
    private UserService userService;

    @Value("${gcs.bucket.name}")
    private String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementSystemController.class);

    @Autowired
    private EnterpriseService enterpriseService;

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path) {
        return "ems/" + path;
    }

    /*
     * 사원 기본정보 화면 페이지 
     * 
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
     * 
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
        
        userService.memRegistModify(RegistrationMap);

        if(RegistrationMap.get("retVal") != null) {
            String retVal = RegistrationMap.get("retVal").toString();

            if("0".equals(retVal)) {
                uploadFileToGCS(imgBankbook, imgBankbookUrl);
                uploadFileToGCS(imgFamilyRL, imgFamilyRLUrl);
                uploadFileToGCS(imgProfile, imgProfileUrl);
                uploadFileToGCS(imgEtc, imgEtcUrl);
                return ResponseEntity.ok(RegistrationMap);
            }
            
            return ResponseEntity.ok(RegistrationMap);
        }
        
     return ResponseEntity.status(500).build();

    }

}
