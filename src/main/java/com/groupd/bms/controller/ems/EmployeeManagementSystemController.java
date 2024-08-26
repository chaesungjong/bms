package com.groupd.bms.controller.ems;

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
import com.groupd.bms.controller.BaseController;
import com.groupd.bms.service.BoardService;
import com.groupd.bms.service.EnterpriseService;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.SHA256Util;
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
     * 사원정보 리스트 가져오기 
     */
    @RequestMapping(value = "/employee_list", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> employeeList( HttpServletRequest request) {

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
        int totalRecords = Integer.parseInt(setPagination(boardService.mng("USER_LIST_CNT", userId, String.valueOf(page), String.valueOf(length), startDate, EndDate, searchType, search, "")));
        // 데이터 가져오기
        List<Map<String, Object>> employeeList = boardService.mngList("USER_LIST", userId, String.valueOf(page), String.valueOf(length), startDate, EndDate, searchType, search, "");

        resMap.put("draw", draw);
        resMap.put("recordsTotal", totalRecords);
        resMap.put("data", employeeList);

        return ResponseEntity.ok(resMap);
    }   

    /*
     * 사원정보 상세 페이지
     */
    @RequestMapping(value = "/employee_detail", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> employeeDetail( HttpServletRequest request) {

        HashMap<String, Object> requestMap = setRequest(request);
        HashMap<String, Object> memInfoMap = userService.memRegistModifyHashMap(requestMap);

        if(memInfoMap != null && memInfoMap.size() > 0) {
            
            String depart_total_info = StringUtil.objectToString(memInfoMap.get("departCode") + " "  + memInfoMap.get("teamCode") + " / " + memInfoMap.get("jobPosition")  + " " + memInfoMap.get("jobTitle"));
            String jobStartDate = StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("jobStartDate"))) + " (" + memInfoMap.get("jobStatus") + ")";
            String jobStatus = StringUtil.objectToString(memInfoMap.get("jobStatus"));
            String jobDate = "";

            if("퇴사".equals(jobStatus)) {
                jobDate = StringUtil.getPeriod(StringUtil.objectToString(memInfoMap.get("jobStartDate")) , jobDate);
            }else{
                jobDate = StringUtil.getPeriod(StringUtil.objectToString(memInfoMap.get("jobStartDate")) , StringUtil.today().replaceAll("-", ""));
            }

            String boardUseYN = "Y".equals(StringUtil.objectToString(memInfoMap.get("boardUseYN"))) ? "가능" : "불가능";

            String birthday = StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("birthday")));
            String Account = StringUtil.objectToString(memInfoMap.get("payGiveType") + " / " + memInfoMap.get("bankAccount"));
            
            HashMap<String, Object> resMap = new  HashMap<String, Object>();
            resMap.put("retVal", "0");                                        // 성공
            resMap.put("userName", memInfoMap.get("name"));                     // 사용자명
            resMap.put("departName", memInfoMap.get("departCode"));             // 부서명
            resMap.put("jobTitle", memInfoMap.get("jobTitle"));                 // 직책
            resMap.put("depart_total_info", depart_total_info);                     // 부서 / 팀 / 직위 / 직책
            resMap.put("jobStartDate", jobStartDate);                               // 입사일
            resMap.put("jobDate", jobDate);                                         // 근무기간
            resMap.put("hireType", memInfoMap.get("hireType"));                 // 채용구분
            resMap.put("jobTitle", memInfoMap.get("jobTitle"));                 // 직책
            resMap.put("birthday", birthday);                                       // 생년월일
            resMap.put("email", memInfoMap.get("email"));                       // 이메일
            resMap.put("emailDepart", memInfoMap.get("emailDepart"));           // 부서 이메일
            resMap.put("hpno", memInfoMap.get("hpno"));                         // 전화 번호
            resMap.put("hpnoDepart", memInfoMap.get("hpnoDepart"));             // 부서 전화 번호
            resMap.put("addr", memInfoMap.get("addr"));                         // 주소
            resMap.put("Account", Account);                                         // 계좌번호
            resMap.put("marriedType", memInfoMap.get("marriedType") );          // 가족관계
            resMap.put("juminNo", memInfoMap.get("juminNo") );                  // 주민번호
            resMap.put("boardUseYN", boardUseYN );                                  // 게시판 사용 가능
            resMap.put("memo", memInfoMap.get("memo") );                        // 메모
            resMap.put("imgProfile", "".equals(StringUtil.objectToString(memInfoMap.get("imgProfile"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgProfile"))); // 프로필 이미지
            resMap.put("imgBankbook", "".equals(StringUtil.objectToString(memInfoMap.get("imgBankbook"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgBankbook"))); //은행 계좌
            resMap.put("imgFamilyRL", "".equals(StringUtil.objectToString(memInfoMap.get("imgFamilyRL"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgFamilyRL"))); // 프로필 이미지
            resMap.put("imgEtc", "".equals(StringUtil.objectToString(memInfoMap.get("imgEtc"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgEtc"))); // 프로필 이미지

            return ResponseEntity.ok(resMap);
            
        }
        
        return ResponseEntity.status(500).build();
    }    

    /*
     * 사원 기본정보 화면 페이지 
     */
    @GetMapping("/add_employees")
    public String add_employees(HttpServletRequest request, Model model) {
        
        HashMap<String, Object> requestMap = setRequest(request);
        HashMap<String, Object> memInfoMap = userService.memRegistModifyHashMap(requestMap);

        if(memInfoMap != null && memInfoMap.size() > 0) {
        
            memInfoMap.put("jobStartDate", StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("jobStartDate"))));
            memInfoMap.put("jobEndDate", StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("jobEndDate"))));
            memInfoMap.put("birthday", StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("birthday"))));

            memInfoMap.put("imgBankbookFileName", "".equals(StringUtil.objectToString(memInfoMap.get("imgBankbook"))) ?  "" : StringUtil.objectToString(memInfoMap.get("imgBankbook")));
            memInfoMap.put("imgBankbook", "".equals(StringUtil.objectToString(memInfoMap.get("imgBankbook"))) ?  "" : "/proxy/" +StringUtil.objectToString(memInfoMap.get("imgBankbook")));
            
            memInfoMap.put("imgFamilyRLFileName", "".equals(StringUtil.objectToString(memInfoMap.get("imgFamilyRL"))) ?  "" :  StringUtil.objectToString(memInfoMap.get("imgFamilyRL")));
            memInfoMap.put("imgFamilyRL", "".equals(StringUtil.objectToString(memInfoMap.get("imgFamilyRL"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgFamilyRL")));

            memInfoMap.put("imgProfileFileName", "".equals(StringUtil.objectToString(memInfoMap.get("imgProfile"))) ?  "" : StringUtil.objectToString(memInfoMap.get("imgProfile")));
            memInfoMap.put("imgProfile", "".equals(StringUtil.objectToString(memInfoMap.get("imgProfile"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgProfile")));

            memInfoMap.put("imgEtcFimeName", "".equals(StringUtil.objectToString(memInfoMap.get("imgEtc"))) ?  "" :StringUtil.objectToString(memInfoMap.get("imgEtc")));
            memInfoMap.put("imgEtc", "".equals(StringUtil.objectToString(memInfoMap.get("imgEtc"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgEtc")));
        
            model.addAttribute("memInfo", memInfoMap);
            model.addAttribute("corrections", "Y");
        
        }


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
    public ResponseEntity<?> Registration( HttpServletRequest request, @RequestParam("imgBankbook") MultipartFile imgBankbook, @RequestParam("imgFamilyRL") MultipartFile imgFamilyRL, @RequestParam("imgProfile") MultipartFile imgProfile, @RequestParam("imgEtc") MultipartFile imgEtc) {
        

        HashMap<String, Object> RegistrationMap = setRequest(request);

        if("Y".equals(StringUtil.objectToString(RegistrationMap.get("corrections")))) 
            RegistrationMap.put("gubun", "MEM_MODIFY"); 
        else 
            RegistrationMap.put("gubun", "MEM_JOIN");

        String imgBankbookUrl = "";
        String imgFamilyRLUrl = "";
        String imgProfileUrl = "";
        String imgEtcUrl  = "";


        RegistrationMap.put("pwd", SHA256Util.hashWithSHA256(StringUtil.objectToString(RegistrationMap.get("pwd"))));
        RegistrationMap.put("birthday",StringUtil.objectToString(RegistrationMap.get("birthday")).replaceAll("-", "") );
        RegistrationMap.put("jobStartDate",StringUtil.objectToString(RegistrationMap.get("jobStartDate")).replaceAll("-", ""));
        RegistrationMap.put("jobEndDate",StringUtil.objectToString(RegistrationMap.get("jobEndDate")).replaceAll("-", ""));

        String imgBankbookFileName = StringUtil.objectToString(RegistrationMap.get("imgBankbookFileName"));
        String imgFamilyRLFileName = StringUtil.objectToString(RegistrationMap.get("imgFamilyRLFileName"));
        String imgProfileFileName = StringUtil.objectToString(RegistrationMap.get("imgProfileFileName"));
        String imgEtcFimeName = StringUtil.objectToString(RegistrationMap.get("imgEtcFimeName"));


        
        if(imgBankbook != null && !imgBankbook.isEmpty())  {
            imgBankbookUrl = setFileName(imgBankbook,UUID.randomUUID().toString());
            RegistrationMap.put("imgBankbook", imgBankbookUrl);
            imgBankbookFileName = "";
        }else if(!"".equals(imgBankbookFileName)){
            RegistrationMap.put("imgBankbook", imgBankbookFileName);
        }


        if(imgFamilyRL != null && !imgFamilyRL.isEmpty()) {
            imgFamilyRLUrl = setFileName(imgFamilyRL,UUID.randomUUID().toString());
            RegistrationMap.put("imgFamilyRL", imgFamilyRLUrl);
            imgFamilyRLFileName = "";
        }else if(!"".equals(imgFamilyRLFileName)){
            RegistrationMap.put("imgFamilyRL", imgFamilyRLFileName);
        }


        if(imgProfile != null && !imgProfile.isEmpty()) {
            imgProfileUrl = setFileName(imgProfile,UUID.randomUUID().toString());
            RegistrationMap.put("imgProfile", imgProfileUrl);
            imgProfileFileName = "";
        }else if(!"".equals(imgProfileFileName)){
            RegistrationMap.put("imgProfile", imgProfileFileName);
        }


        if(imgEtc != null && !imgEtc.isEmpty()) {
            imgEtcUrl = setFileName(imgEtc,UUID.randomUUID().toString());
            RegistrationMap.put("imgEtc", imgEtcUrl);
            imgEtcFimeName = "";
        }else if(!"".equals(imgEtcFimeName)){
            RegistrationMap.put("imgEtc", imgEtcFimeName);
        }
        
        //사원 등록 처리
        userService.memRegistModify(RegistrationMap);

        //사원 등록 결과
        if(RegistrationMap.get("retVal") != null) {

            String retVal = RegistrationMap.get("retVal").toString();

            //사원 등록 성공
            if("0".equals(retVal)) {
                
                // if(!"".equals(imgBankbookUrl) && "".equals(imgBankbookFileName))    uploadFileToGCS(imgBankbook, imgBankbookUrl);
                // if(!"".equals(imgFamilyRL) && "".equals(imgFamilyRLFileName))       uploadFileToGCS(imgFamilyRL, imgFamilyRLUrl);
                // if(!"".equals(imgProfile)&& "".equals(imgProfileFileName))          uploadFileToGCS(imgProfile, imgProfileUrl);
                // if(!"".equals(imgEtcUrl)&& "".equals(imgEtcFimeName))               uploadFileToGCS(imgEtc, imgEtcUrl);
                
                return ResponseEntity.ok(RegistrationMap);
            }
            
            return ResponseEntity.ok(RegistrationMap);
        }
        
     return ResponseEntity.status(500).build();

    }

}
