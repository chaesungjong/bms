package com.groupd.bms.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupd.bms.model.User;
import com.groupd.bms.repository.UserRepository;

/**
 * UserService
 * 사용자 정보를 관리하기 위한 서비스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.controller.login.LoginController
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
    public HashMap<String, Object> login(String userID, String password) {

      //사용자가 입력한 아이디와 비밀번호를 체크한다.
      HashMap<String, Object> resultHashMap = userRepository.checkuserInfotmation(userID, password);


      if (resultHashMap != null ) {
        User u = new User();
        u.setId("test");
        u.setUsername("이름");
        u.setPassword("1234");
      }

      return null; // or throw exception
    }

    /**
     * 사용자 등록을 처리한다.
     * @param username
     * @param password
     * @param name
     * @param email
     * @param firstName
     * @param lastName
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> register(String userid,String password, String name,String firstName,String lastName, String email) {
      HashMap<String, Object> user = userRepository.insertRegister( userid, password, name, firstName, lastName, email);
      return null; // or throw exception
  }
    
}
