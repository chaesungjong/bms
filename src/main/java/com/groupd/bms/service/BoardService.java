package com.groupd.bms.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.repository.BoardRepository;
import java.util.HashMap;
import java.util.List;

/**
 * BoardService
 * 게시판 정보 가져오는 서비스
 * @version 1.0
 * @since 2024.06.30
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * 게시판 타입 조회
     * @return
     */
    public HashMap<String, Object> codeMgtViewBoardType(Map<String, Object> params) {
        return boardRepository.codeMgtViewBoardType(params);
    }

    /**
     * 게시판 목록 가져오기 
     * @return
     */
    public List<Map<String, Object>> mngList(Map<String, Object> params) {
        return boardRepository.mngList(params);
    }

    /**
     * 게시판 목록 가져오기 
     * @return
     */
    public List<Map<String, Object>> mngList(String gubun,String userID, String pageno, String pagesize, String etcParam) {
        HashMap<String, Object> boardMap = new HashMap<>();
        boardMap.put("gubun", gubun);
        boardMap.put("userid", userID);
        boardMap.put("PageNo", pageno);
        boardMap.put("PageSize", pagesize);
        boardMap.put("gubun", gubun);
        boardMap.put("etcParam", etcParam);
        return boardRepository.mngList(boardMap);
    }

    /**
     * 게시판 등록/수정 하기
     * @return
     */
    public void boardRegModify(Map<String, Object> params) {
         boardRepository.boardRegModify(params);
    }

    /**
     * 세팅 게시판 이미지 저장/수정 하기
     * @return
     */
    public void boardSettingRegModify(Map<String, Object> params) {
        boardRepository.boardSettingRegModify(params);
   }

}
