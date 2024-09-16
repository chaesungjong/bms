package com.groupd.bms.service;

import java.util.Map;

/**
 * BoardService
 * 게시판 정보 가져오는 서비스
 * @version 1.0
 * @since 2024.09.14
 */
public interface BoardService {

    /**
     * 게시판 등록/수정 하기
     * @return
     */
    public void boardRegModify(Map<String, Object> params);

    /**
     * 세팅 게시판 이미지 저장/수정 하기
     * @return
     */
    public void boardSettingRegModify(Map<String, Object> params);

}
