package com.groupd.bms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.repository.UserRepository;
import com.groupd.bms.util.MapperUtil;
import com.groupd.bms.util.StringUtil;

/**
 * UserService
 * 사용자 정보를 관리하기 위한 서비스
 * @version 1.0
 * @since 2024.04.26
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * 사용자 로그인을 처리한다.
     * @param userID
     * @param password
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> login(MemberLogin memberLogin) {

      HashMap<String, Object> result = new HashMap<>();

      //사용자가 입력한 아이디와 비밀번호를 체크한다.
      userRepository.memLoginProc(memberLogin);

      String retVal = StringUtil.objectToString(memberLogin.getRetVal()); //로그인 성공 여부
      String errMsg = StringUtil.objectToString(memberLogin.getErrMsg()); //에러 메시지

      if("0".equals(retVal)){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("gubun", "MEM_USERINFO");
        hashMap.put("userid", memberLogin.getUserid());
        result.put("member", MapperUtil.mapToMember(userRepository.memUserInfo(hashMap)));
      }
      
      result.put("retVal", retVal);
      result.put("errMsg", errMsg);

      return result;
    }

    /**
     * 회원가입/수정/정보 기능을 한다. 
     * @param params
     */
    public void memRegistModify(Map<String, Object> params) {
      userRepository.memRegistModify(params);
    }

    /**
     * 회원 정보를 가져온다.
     * @param params
     */
    public HashMap<String, Object> memRegistModifyHashMap(Map<String, Object> params) {
      params.put("gubun", "MEM_USERINFO");
      return userRepository.memRegistModifyHashMap(params);
    }
    
}
