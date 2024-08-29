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

    /**
     * 用户输入参数异常，需用户处理
     * @param e
     * @return
     */
    @ExceptionHandler(ParamValidateException.class)
    public Result paramValidateException(ParamValidateException e) {
        String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                          ? e.getMessage() : "参数错误";
        return Result.error(errorMessage);
    }

    /**
     * 数据为空异常，需要补充数据
     * @param e
     * @return
     */
    @ExceptionHandler({ResultNullDataException.class})
    public Result resultNullDataException(ResultNullDataException e) {
        String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                          ? e.getMessage() : "数据为空";
        return Result.error(errorMessage);
    }

    /**
     * 系统异常，代码或服务器错误，具体异常内容不在系统显示，打印到后台日志
     * @param e
     * @return
     */
    @ExceptionHandler({RandomSystemException.class})
    public Result randomSystemException(RandomSystemException e) {
        String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                          ? e.getMessage() : "系统异常";
        return Result.error(errorMessage);
    }

}
