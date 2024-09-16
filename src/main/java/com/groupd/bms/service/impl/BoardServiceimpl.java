package com.groupd.bms.service.impl;

import com.groupd.bms.repository.BoardRepository;
import com.groupd.bms.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 게시판 정보 가져오는 서비스
 * @version 1.0
 * @since 2024.09.10
 */
@Service
public class BoardServiceimpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
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
