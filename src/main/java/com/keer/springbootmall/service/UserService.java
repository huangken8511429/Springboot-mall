package com.keer.springbootmall.service;

import com.keer.springbootmall.dto.UserLoginRequest;
import com.keer.springbootmall.dto.UserRegisterRequest;
import com.keer.springbootmall.model.User;

public interface UserService {

    User login(UserLoginRequest userLoginRequest);

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);




}
