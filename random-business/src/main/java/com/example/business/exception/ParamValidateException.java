package com.example.business.exception;

/**
 * 参数错误异常
 */
public class ParamValidateException extends Throwable{

    public ParamValidateException() {

    }

    public ParamValidateException(String message) {
        super(message);
    }

}
