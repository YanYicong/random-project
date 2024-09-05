package com.example.business.exception;

/**
 * 用户信息异常
 */
public class UserInfoException extends Exception{

    UserInfoException() {

    }

    public UserInfoException(String message) {
        super (message);
    }
}
