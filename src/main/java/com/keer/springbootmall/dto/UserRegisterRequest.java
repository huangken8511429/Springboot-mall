package com.keer.springbootmall.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegisterRequest {
     @Email
     @NotBlank //設定不可以是Null也不可以是空字串
    private String email;
     @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
