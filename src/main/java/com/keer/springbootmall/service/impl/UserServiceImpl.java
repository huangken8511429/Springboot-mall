package com.keer.springbootmall.service.impl;

import com.keer.springbootmall.dao.UserDao;
import com.keer.springbootmall.dto.UserLoginRequest;
import com.keer.springbootmall.dto.UserRegisterRequest;
import com.keer.springbootmall.model.User;
import com.keer.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component  //在該Service實作判斷的邏輯，DAO單純只是跟資料庫做溝通
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
       //檢查user是否存在
        if (user == null) {
            log.warn("該Email {} 還沒註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5生成密碼的雜湊值

        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        //比較密碼
        if (user.getPassword().equals(hashedPassword))
            return user;
        else {
            log.warn("Email {} 的密碼錯誤", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查註冊的email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail()); //先在資料庫找該Email的資料，如果有資料，代表email已經註冊過帳號了，
        //我們就停止該email的註冊並丟出Exception
        if (user != null) {
            log.warn("該 Email {} 已經被註冊", userRegisterRequest.getEmail()); //指定log等級warn，{}表示變數，在這邊就是後面 userRegisterRequest.getEmail()的值
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
       //使用MD5來加密密碼，以防資料庫外洩，造成資安問題。
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);


    }
}
