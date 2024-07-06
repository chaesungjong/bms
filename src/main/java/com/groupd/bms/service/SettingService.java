package com.groupd.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.repository.NaverPlaceBoardRepository;
import java.util.HashMap;
import java.util.List;
/**
 * SettingService
 * 그룹디 세팅 게시판 정보를 관리하기 위한 서비스
 * @version 1.0
 * @since 2024.06.23
 */
@Service
public class SettingService {

    @Autowired
    private NaverPlaceBoardRepository naverPlaceBoardRepository;

    /**
     * 네이버 플레이스 게시판 정보 등록/수정/삭제/조회
     * @param params
     */
    public void manageNaverPlaceBoard(HashMap<String, Object> params) {
        naverPlaceBoardRepository.manageNaverPlaceBoard(params);
    }

    /**
     * 네이버 플레이스 게시판 정보 조회
     * @param params
     * @return List<HashMap<String, Object>>
     */
    public List<HashMap<String, Object>> selectNaverPlaceBoard(HashMap<String, Object> params){
        return naverPlaceBoardRepository.selectNaverPlaceBoard(params);
    }

    
}
