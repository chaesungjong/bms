package com.groupd.bms.service;

import java.util.HashMap;
import com.groupd.bms.model.MemberLogin;

/**
 * 사용자 정보를 처리하는 서비스 클래스
 * @version 1.0
 * @since 2024.09.10
 */
public interface UserService {

     /**
     * 사용자 로그인을 처리한다.
     * @param MemberLogin
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> login(MemberLogin memberLogin);

    /**
     * 사용자 정보를 조회한다.
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> getUserInfo(HashMap<String, Object> params);


    /**
     * 사용자 정보를 수정한다.
     * @param HashMap<String, Object>
     * @return String
     */
    public String memRegistModify(HashMap<String, Object> params);
    
}
