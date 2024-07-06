package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.HashMap;

@Mapper
public interface NaverPlaceBoardRepository {

    /**
     * 네이버 플레이스 게시판 정보 등록/수정/삭제/조회
     * 
     * @return
     */
    void manageNaverPlaceBoard(HashMap<String, Object> params);

    List<HashMap<String, Object>> selectNaverPlaceBoard(HashMap<String, Object> params);
}
