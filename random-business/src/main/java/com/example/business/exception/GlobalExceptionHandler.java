package com.example.business.exception;

import com.example.business.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        return Result.error("系统异常");
    }

    @ExceptionHandler
    public Result paramValidateException(ParamValidateException e) {
        return Result.error("参数错误");
    }

    @ExceptionHandler
    public Result proportionException(ProportionException e) {
        return Result.error("概率定义错误");
    }
}
