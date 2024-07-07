package com.groupd.bms.controller.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.groupd.bms.model.Member;
import com.groupd.bms.service.BoardService;
import com.groupd.bms.util.StringUtil;
import com.groupd.bms.controller.BaseController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SettingController
 * 그룹디 광고 설정 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("setting")
public class SettingController extends BaseController {

    @Autowired
    private BoardService boardService;

    /**
     * 구분에 따라 세팅 페이지를 보여준다.
     * @param model 'NAV_PL'
     * @return naverPlace.html
     */
    @GetMapping("/board")
    public String board(HttpServletRequest request, Model model) {
        // 로그인 시도
        HashMap<String, Object> registrationMap = setRequest(request);
        Member member = (Member) request.getSession().getAttribute("member");
        String etcParam = (String) registrationMap.get("etcParam");

        HashMap<String, Object> boardMap = new HashMap<>();
        boardMap.put("etcParam", etcParam);
        boardMap.put("userid", member.getUserid());
        boardMap.put("PageNo", "1");
        boardMap.put("PageSize", "10");

        // 게시판 리스트 및 카운트 조회
        boardMap.put("gubun", "BOARD_LIST_CNT");
        List<Map<String, Object>> boardListCntMap = boardService.mngList(boardMap);

        boardMap.put("gubun", "BOARD_LIST");
        List<Map<String, Object>> boardListMap = boardService.mngList(boardMap);

        model.addAttribute("boardListCnt", boardListCntMap);
        model.addAttribute("boardList", boardListMap);

        if ("NAV_PL".equals(etcParam)) {
            return "setting/naverPlace";
        } else {
            return "setting/board";
        }
    }

    /**
     * 네이버 플레이스 등록/삭제/조회/수정을 처리한다.
     * @param request HTTP 요청 객체
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/dataTable.json", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> dataTable(HttpServletRequest request) {
        // 로그인 시도
        HashMap<String, Object> registrationMap = setRequest(request);
        if (registrationMap == null) {
            return ResponseEntity.status(500).build();
        }

        Member member = (Member) request.getSession().getAttribute("member");
        String etcParam = (String) registrationMap.get("etcParam");

        HashMap<String, Object> boardMap = new HashMap<>();
        boardMap.put("etcParam", etcParam);
        boardMap.put("userid", member.getUserid());
        boardMap.put("PageNo", "1");
        boardMap.put("PageSize", "100");
        boardMap.put("gubun", "BOARD_LIST");

        // 게시판 리스트 조회
        List<Map<String, Object>> boardListMap = boardService.mngList(boardMap);

        // 반환할 데이터를 wrapping
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", boardListMap);

        return ResponseEntity.ok(response);
    }

    /**
     * 네이버 플레이스 세팅 화면을 보여준다.
     * @param model
     * @return naverPlace.html
     */
    @GetMapping("/naverForm")
    public String naverPlaceSetting(HttpServletRequest request, Model model) {
        // 로그인 시도
        HashMap<String, Object> registrationMap = setRequest(request);
        Member member = (Member) request.getSession().getAttribute("member");

        String seq = (String) registrationMap.get("seq");

        if (seq != null) {
            HashMap<String, Object> boardMap = new HashMap<>();
            boardMap.put("etcParam", seq);
            boardMap.put("userid", member.getUserid());
            boardMap.put("PageNo", "0");
            boardMap.put("PageSize", "0");
            boardMap.put("gubun", "BOARD_SETTING_LIST");

            // 게시판 설정 리스트 조회
            List<Map<String, Object>> boardListMap = boardService.mngList(boardMap);

            if (boardListMap != null && !boardListMap.isEmpty()) {
                model.addAttribute("board", boardListMap.get(0));

                for (int i = 0; i < boardListMap.size(); i++) {
                    model.addAttribute("imgPath_" + i, "/setting/proxy?url="+boardListMap.get(i).get("imgPath"));
                    model.addAttribute("imgPath_seq_" + i, boardListMap.get(i).get("seq"));
                }
            }
        }

        return "setting/naverForm";
    }

    /**
     * 네이버 플레이스를 등록/수정 처리한다.
     * @param request HTTP 요청 객체
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/naverPlaceRegist.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> naverPlaceRegist(HttpServletRequest request) {
        HashMap<String, Object> registrationMap = new HashMap<>();
        Map<String, String> imgPathMap = new HashMap<>();
        Map<String, String> imgPathSeqMap = new HashMap<>();
        Member member = (Member) request.getSession().getAttribute("member");

        String boardSeqParam = request.getParameter("boardSeq");
        String gdUserKeyParam = request.getParameter("gd_userkey");
        String siteKeyParam = request.getParameter("sitekey");

        int boardSeq = parseIntegerParameter(boardSeqParam);
        int gdUserKey = parseIntegerParameter(gdUserKeyParam);
        int siteKey = parseIntegerParameter(siteKeyParam);

        for (int i = 0; i <= 10; i++) {
            String key = "imgPath_" + i;
            String keySeq = "imgPath_seq_" + i;
            String value = request.getParameter(key);
            String valueSeq = request.getParameter(keySeq);
            if (value != null)  imgPathMap.put(key, value);
            if (valueSeq != null)  imgPathSeqMap.put(keySeq, valueSeq);
            
        }

        registrationMap.put("gubun", boardSeq == 0 ? "REGIST" : "MODIFY");
        registrationMap.put("loginUserid", member.getUserid());
        registrationMap.put("loginUserip", request.getRemoteAddr());
        registrationMap.put("boardSeq", boardSeq);
        registrationMap.put("boardType", "NAV_PL");
        registrationMap.put("title", getParameterOrDefault(request, "contents", ""));
        registrationMap.put("contents", getParameterOrDefault(request, "contents", ""));
        registrationMap.put("state", getParameterOrDefault(request, "state", "Y"));
        registrationMap.put("gd_depart", getParameterOrDefault(request, "gd_depart", ""));
        registrationMap.put("gd_userkey", gdUserKey);
        registrationMap.put("sitekey", siteKey);
        registrationMap.put("siteNameDI", getParameterOrDefault(request, "siteNameDI", ""));
        registrationMap.put("retBoardSeq", -10);
        registrationMap.put("retVal", -10);
        registrationMap.put("retMsg", "");

        boardService.boardRegModify(registrationMap);
        System.out.println("RegistrationMap : " + registrationMap);

        // 게시판 등록 성공 시 이미지 설정 등록/수정
        if ("0".equals(StringUtil.objectToString(registrationMap.get("retVal")))) {

            for (int i = 0; i <= 10; i++) {

                String imgPath = StringUtil.objectToString(imgPathMap.get("imgPath_" + i)).replace("/setting/proxy?url=", "");
                String imgPathSeq = StringUtil.objectToString(imgPathSeqMap.get("imgPath_seq_" + i));
                
                HashMap<String, Object> imageMap = new HashMap<>();
                imageMap.put("gubun", imgPathSeq.isEmpty() ? "REGIST" : "MODIFY");
                imageMap.put("loginUserid", member.getUserid());
                imageMap.put("loginUserip", request.getRemoteAddr());
                imageMap.put("boardSeq", boardSeq == 0 ? StringUtil.objectToString(registrationMap.get("retBoardSeq")) : boardSeq);
                imageMap.put("boardSTSeq", StringUtil.objectToString(imgPathSeqMap.get("imgPath_seq_" + i)));
                imageMap.put("bs_title", getParameterOrDefault(request, "contents", ""));
                imageMap.put("bs_contents", "");
                imageMap.put("bs_contents2", getParameterOrDefault(request, "contents", ""));
                imageMap.put("imgPath", imgPath);
                imageMap.put("linkUrl", imgPath);
                imageMap.put("viewSeq", i);
                imageMap.put("state", "Y");
                boardService.boardSettingRegModify(imageMap);
                
            }
        }

        return ResponseEntity.ok(registrationMap);
    }

    /**
     * 요청 파라미터를 정수로 변환한다.
     *
     * @param parameter 변환할 파라미터
     * @return 변환된 정수 값
     */
    private int parseIntegerParameter(String parameter) {
        return (parameter == null || parameter.isEmpty()) ? 0 : Integer.parseInt(parameter);
    }

    /**
     * 요청 파라미터를 가져오거나 기본 값을 반환한다.
     *
     * @param request       HTTP 요청 객체
     * @param parameterName 파라미터 이름
     * @param defaultValue  기본 값
     * @return 파라미터 값 또는 기본 값
     */
    private String getParameterOrDefault(HttpServletRequest request, String parameterName, String defaultValue) {
        String parameter = request.getParameter(parameterName);
        return (parameter != null) ? parameter : defaultValue;
    }

        @CrossOrigin
    @RequestMapping("/proxy")
    public ResponseEntity<byte[]> proxy(@RequestParam String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ORIGIN, "http://localhost:8080");

        ResponseEntity<byte[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new org.springframework.http.HttpEntity<>(headers),
            byte[].class
        );

        return response;
    }
}
