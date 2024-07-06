package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Mapper
public interface BoardRepository {

    /**
     * 게시판 타입 조회
     * @return
     */
    HashMap<String, Object> codeMgtViewBoardType(Map<String, Object> params);

    /**
     * 게시판 종류 가져오기
     * @return
     */
    List<Map<String, Object>> mngList(Map<String, Object> params);

    /**
     * 게시판 등록/수정 하기 
     * @return
     */
    void boardRegModify(Map<String, Object> params);

    /**
     * 세팅 게시판 이미지 저장/수정 하기
     * @return
     */
    void boardSettingRegModify(Map<String, Object> params);
}
