package com.groupd.bms.service;

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
     * 사용자 정보를 조회한다.
     * @param username
     * @param password
     * @return User
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // or throw exception
    }
    
}
