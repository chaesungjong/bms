package com.groupd.bms.service.impl;

import com.groupd.bms.service.UserService;
import com.groupd.bms.util.MapperUtil;
import com.groupd.bms.util.StringUtil;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.repository.UserRepository;

/**
 * 사용자 정보를 처리하는 서비스 클래스
 * @version 1.0
 * @since 2024.09.10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 사용자 로그인을 처리한다.
     * @param MemberLogin
     * @return HashMap<String, Object>
     */
    @Override
    public HashMap<String, Object> login(MemberLogin memberLogin) {
        
        HashMap<String, Object> result = new HashMap<>();

        //사용자가 입력한 아이디와 비밀번호를 체크한다.
        userRepository.memLoginProc(memberLogin);

        String retVal = StringUtil.objectToString(memberLogin.getRetVal()); //로그인 성공 여부
        String errMsg = StringUtil.objectToString(memberLogin.getErrMsg()); //에러 메시지

        //아이디 및 비밀번호가 일치 할 시, 사용자 정보를 가져온다.
        if("0".equals(retVal)){

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("gubun", "MEM_USERINFO");
            hashMap.put("userid", memberLogin.getUserid());
            hashMap.put("ip", memberLogin.getIp());
            result.put("member", MapperUtil.mapToMember(userRepository.memRegistModifyHashMap(hashMap)));
            
        }
      
        result.put("retVal", retVal);
        result.put("errMsg", errMsg);

        return result;
        
    }

    /**
     * 사용자 정보를 조회한다.
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     */
    @Override
    public HashMap<String, Object> getUserInfo(HashMap<String, Object> params) {
        params.put("gubun", "MEM_USERINFO");
        return userRepository.memRegistModifyHashMap(params);
    }

    /**
     * 사용자 정보를 수정한다.
     * @param HashMap<String, Object>
     * @return String
     */
    public String memRegistModify(HashMap<String, Object> params) {
        userRepository.memRegistModify(params);
        return StringUtil.objectToString(params.get("retVal"), "9999");
    }

    
}
