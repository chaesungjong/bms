package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;

import com.groupd.bms.model.MemberLogin;

import java.util.HashMap; 

@Mapper
public interface UserRepository {
    
    /**
     * 사용자 로그인 조회
     * @param username
     * @return
     */
    void memLoginProc(MemberLogin memberLogin);

    // 사용자 정보 등록
    HashMap<String, Object> insertRegister(String userid, String password, String name, String firstName, String lastName, String email);
    
}
