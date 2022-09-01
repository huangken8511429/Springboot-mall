package com.keer.springbootmall.dao;

import com.keer.springbootmall.dto.UserRegisterRequest;
import com.keer.springbootmall.model.User;
import org.springframework.stereotype.Component;


public interface UserDao {

    User getUserByEmail(String email);

    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
