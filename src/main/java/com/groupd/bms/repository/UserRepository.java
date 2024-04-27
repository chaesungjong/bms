package com.groupd.bms.repository;

import org.apache.ibatis.annotations.Mapper;

import com.groupd.bms.model.User;


@Mapper
public interface UserRepository {
    
    User findByUsername(String username);
    
}
