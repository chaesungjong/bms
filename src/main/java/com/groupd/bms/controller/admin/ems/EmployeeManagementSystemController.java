package com.groupd.bms.controller.admin.ems;

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
import com.groupd.bms.service.CommonService;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.SHA256Util;
import com.groupd.bms.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import com.groupd.bms.model.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * EmployeeManagementSystemController
 * 사원 관리 컨트롤러
 */
@Controller
@RequestMapping("admin/ems")
public class EmployeeManagementSystemController extends BaseController{
    
    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementSystemController.class);

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path) {
        logger.debug(" 접속 경로 : ems/" + path);
        return "admin/ems/" + path;
    }

    /*
     * 사원정보 리스트 가져오기 
     */
    @RequestMapping(value = "/employee_list", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> employeeList( HttpServletRequest request) {
        logger.debug("사원 리스트 가져오기 : employee_list ");

        HashMap<String, Object> registrationMap = setRequest(request);
        HashMap<String, Object> resMap = new HashMap<String, Object>();

        String startDate  = StringUtil.dataformat(StringUtil.objectToString(registrationMap.get("fr_date")));               //시작일
        String EndDate    = StringUtil.dataformat(StringUtil.objectToString(registrationMap.get("to_date"))); ;             //종료일
        String searchType = StringUtil.objectToString(registrationMap.get("searchType"));                                   //검색타입     
        String search     = StringUtil.objectToString(registrationMap.get("search"));                                       //검색어 
        String userId     = StringUtil.objectToString(registrationMap.get("userId"));                                       //사용자ID


        // DataTables 파라미터 가져오기
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));

        // 페이지 번호 계산
        int page = start / length + 1;
        // 전체 레코드 수 가져오기
        int totalRecords = Integer.parseInt(setPagination(commonService.mng("USER_LIST_CNT", userId, String.valueOf(page), String.valueOf(length), startDate, EndDate, searchType, search, "")));
        // 데이터 가져오기
        List<Map<String, Object>> employeeList = commonService.mngList("USER_LIST", userId, String.valueOf(page), String.valueOf(length), startDate, EndDate, searchType, search, "");

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
        HashMap<String, Object> memInfoMap = userService.getUserInfo(requestMap);

        if(memInfoMap != null && memInfoMap.size() > 0) {
            
            String depart_total_info = StringUtil.objectToString(memInfoMap.get("departCode") + " / "  + memInfoMap.get("teamCode") + " / " + memInfoMap.get("jobPosition")  + " / " + memInfoMap.get("jobTitle"));
            String jobStartDate = StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("jobStartDate"))) + " (" + memInfoMap.get("jobStatus") + ")";
            String jobStatus = StringUtil.objectToString(memInfoMap.get("jobStatus"));
            String jobDate = "";

            if("퇴사".equals(jobStatus)) {
                jobDate = StringUtil.getPeriod(StringUtil.objectToString(memInfoMap.get("jobStartDate")) , StringUtil.objectToString(memInfoMap.get("jobEndDate")));
            }else{
                jobDate = StringUtil.getPeriod(StringUtil.objectToString(memInfoMap.get("jobStartDate")) , StringUtil.today().replaceAll("-", ""));
            }

            String boardUseYN = "Y".equals(StringUtil.objectToString(memInfoMap.get("boardUseYN"))) ? "가능" : "불가능";

            String birthday = StringUtil.dataformat(StringUtil.objectToString(memInfoMap.get("birthday")));
            String Account = StringUtil.objectToString(memInfoMap.get("payGiveType") + " / " + memInfoMap.get("bankAccount"));
            
            HashMap<String, Object> resMap = new  HashMap<String, Object>();
            resMap.put("retVal", "0");                                                                                                                                                              // 성공
            resMap.put("userName", memInfoMap.get("name"));                                                                                                                                           // 사용자명
            resMap.put("departName", memInfoMap.get("departCode"));                                                                                                                                   // 부서명
            resMap.put("jobTitle", memInfoMap.get("jobTitle"));                                                                                                                                       // 직책
            resMap.put("depart_total_info", depart_total_info);                                                                                                                                           // 부서 / 팀 / 직위 / 직책
            resMap.put("jobStartDate", jobStartDate);                                                                                                                                                     // 입사일
            resMap.put("jobDate", jobDate);                                                                                                                                                               // 근무기간
            resMap.put("hireType", memInfoMap.get("hireType"));                                                                                                                                       // 채용구분
            resMap.put("jobTitle", memInfoMap.get("jobTitle"));;                                                                                                                                      // 직책
            resMap.put("birthday", birthday);           ;                                                                                                                                                 // 생년월일
            resMap.put("email", memInfoMap.get("email"));                                                                                                                                             // 이메일
            resMap.put("emailDepart", memInfoMap.get("emailDepart"));                                                                                                                                 // 부서 이메일
            resMap.put("hpno", memInfoMap.get("hpno"));                                                                                                                                               // 전화 번호
            resMap.put("hpnoDepart", memInfoMap.get("hpnoDepart"));                                                                                                                                   // 부서 전화 번호
            resMap.put("addr", memInfoMap.get("addr"));                                                                                                                                               // 주소
            resMap.put("Account", Account);                                                                                                                                                               // 계좌번호
            resMap.put("marriedType", memInfoMap.get("marriedType") );                                                                                                                                // 가족관계
            resMap.put("juminNo", memInfoMap.get("juminNo") );                                                                                                                                        // 주민번호
            resMap.put("boardUseYN", boardUseYN );                                                                                                                                                        // 게시판 사용 가능
            resMap.put("memo", memInfoMap.get("memo") );                                                                                                                                              // 메모
            resMap.put("imgProfile", "".equals(StringUtil.objectToString(memInfoMap.get("imgProfile"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgProfile")));             // 프로필 이미지
            resMap.put("imgBankbook", "".equals(StringUtil.objectToString(memInfoMap.get("imgBankbook"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgBankbook")));          // 은행 계좌
            resMap.put("imgFamilyRL", "".equals(StringUtil.objectToString(memInfoMap.get("imgFamilyRL"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgFamilyRL")));          // 프로필 이미지
            resMap.put("imgEtc", "".equals(StringUtil.objectToString(memInfoMap.get("imgEtc"))) ?  "" : "/proxy/" + StringUtil.objectToString(memInfoMap.get("imgEtc")));                         // 프로필 이미지

            return ResponseEntity.ok(resMap);
            
        }
        
        return ResponseEntity.status(500).build();
    }    

    /*
     * 사원 기본정보 화면 페이지 
     */
    @RequestMapping(value = "/add_employees", method = { RequestMethod.POST, RequestMethod.GET })
    public String add_employees(HttpServletRequest request, Model model) {

        // GET 요청인지 확인
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // GET 요청일 경우 처리할 로직 작성
            // 예: 다른 페이지로 리다이렉트, 에러 메시지 출력 등
            return "redirect:/admin/ems/employees"; 
        }
        
        HashMap<String, Object> requestMap = setRequest(request);
        HashMap<String, Object> memInfoMap = userService.getUserInfo(requestMap);

        //유저 정보 가져오기
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

            String userId = StringUtil.objectToString(requestMap.get("userId"));
            String emsIDString = StringUtil.objectToString(requestMap.get("userid"));

            if(userId.equals(emsIDString) && !"".equals(emsIDString)) model.addAttribute("pwdChange", "Y");
        
        }


        //서명 리스트 불러오기
        List<Map<String, Object>> departNameList = commonService.codeMgtViewList("LIST", "departName", "","");
        model.addAttribute("departNameList", departNameList);

        //팀명 리스트 불러오기
        List<Map<String, Object>> teamNameList = commonService.codeMgtViewList("LIST", "teamName", "","");
        model.addAttribute("teamNameList", teamNameList);

        //직급 리스트 불러오기
        List<Map<String, Object>> jobPositionList = commonService.codeMgtViewList("LIST", "jobPosition", "","");
        model.addAttribute("jobPositionList", jobPositionList);

        //직책 리스트 불러오기
        List<Map<String, Object>> jobTitleList = commonService.codeMgtViewList("LIST", "jobTitle", "","");
        model.addAttribute("jobTitleList", jobTitleList);

        //재직 상태 리스트 불러오기
        List<Map<String, Object>> jobStatusList = commonService.codeMgtViewList("LIST", "jobStatus", "","");
        model.addAttribute("jobStatusList", jobStatusList);

        //채용구분 리스트 불러오기
        List<Map<String, Object>> hireTypeList = commonService.codeMgtViewList("LIST", "hireType", "","");
        model.addAttribute("hireTypeList", hireTypeList);

        //양/음력 구분 리스트 불러오기
        List<Map<String, Object>> calendarTypeList = commonService.codeMgtViewList("LIST", "calendarType", "","");
        model.addAttribute("calendarTypeList", calendarTypeList);
        
        //급여지급방법  리스트 불러오기
        List<Map<String, Object>> payGiveTypeList = commonService.codeMgtViewList("LIST", "payGiveType", "","");
        model.addAttribute("payGiveTypeList", payGiveTypeList);

        //결혼여부 리스트 불러오기
        List<Map<String, Object>> marriedTypeList = commonService.codeMgtViewList("LIST", "marriedType", "","");
        model.addAttribute("marriedTypeList", marriedTypeList);

        return "admin/ems/add_employees" ;
    }

    /*
     * 사원 등록 처리
     */
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST })
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

        //비밀번호 암호화
        if(!"".equals(StringUtil.objectToString(RegistrationMap.get("pwd")))){
            RegistrationMap.put("pwd", SHA256Util.hashWithSHA256(StringUtil.objectToString(RegistrationMap.get("pwd"))));
        }

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
                
                if(!"".equals(imgBankbookUrl) && "".equals(imgBankbookFileName))    uploadFileToGCS(imgBankbook, imgBankbookUrl);
                if(!"".equals(imgFamilyRLUrl) && "".equals(imgFamilyRLFileName))    uploadFileToGCS(imgFamilyRL, imgFamilyRLUrl);
                if(!"".equals(imgProfileUrl)&& "".equals(imgProfileFileName))       uploadFileToGCS(imgProfile, imgProfileUrl);
                if(!"".equals(imgEtcUrl)&& "".equals(imgEtcFimeName))               uploadFileToGCS(imgEtc, imgEtcUrl);
                
                return ResponseEntity.ok(RegistrationMap);
            }
            
            return ResponseEntity.ok(RegistrationMap);
        }
        
     return ResponseEntity.status(500).build();

    }

    /* 
     * 사원 비밀번호 변경 하기 
     */
    @RequestMapping(value = "/pwdChange.do", method = { RequestMethod.POST})
    public ResponseEntity<?> pwdChange( HttpServletRequest request ) {

        HashMap<String, Object> inputnMap = setRequest(request);
        HashMap<String, Object> resnMap = new HashMap<String, Object>();

        Member member = (Member) request.getSession().getAttribute("member");

        String userPwd =  StringUtil.objectToString(member.getPwd());
        String inputPwd = SHA256Util.hashWithSHA256(StringUtil.objectToString(inputnMap.get("now_pwd")));

        //현재 비밀번호가 일치하지 않을 경우
        if(!userPwd.equals(inputPwd)){

            resnMap.put("retVal", "1");
            resnMap.put("retMsg", "현재 비밀번호가 일치하지 않습니다.");
            
            return ResponseEntity.ok(resnMap);
        }

        //비밀번호 변경 처리
        String pwdNew = SHA256Util.hashWithSHA256(StringUtil.objectToString(inputnMap.get("new_pwd")));

        inputnMap.put("pwdNew", pwdNew);
        inputnMap.put("pwd", userPwd);
        inputnMap.put("gubun", "PWD_MODIFY");
        inputnMap.put("userid", member.getUserid());
        
        //비밀번호 변경 처리 
        userService.memRegistModify(inputnMap);

        if(inputnMap.get("retVal") != null) {

            String retVal = inputnMap.get("retVal").toString();

            //비밀번호 변경 성공
            if("0".equals(retVal)) {
                
                resnMap.put("retVal", "0");
                resnMap.put("retMsg", "비밀번호가 변경되었습니다.");
                member.setPwd(pwdNew);
                request.getSession().setAttribute("member", member);
            }
            
        }

        return ResponseEntity.ok(inputnMap);
    }

}
