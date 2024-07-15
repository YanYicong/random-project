package com.example.business.exception;

import com.example.business.utils.Result;
import com.example.business.utils.page.TableDataInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParamValidateException.class)
    public Result paramValidateException(ParamValidateException e) {
        return Result.error("参数错误");
    }

    @ExceptionHandler(ProportionException.class)
    public Result proportionException(ProportionException e) {
        return Result.error("概率定义错误");
    }
//
//    @ExceptionHandler(Exception.class)
//    public Result handlerException(Exception e){
//        return Result.error("系统异常");
//    }
}
