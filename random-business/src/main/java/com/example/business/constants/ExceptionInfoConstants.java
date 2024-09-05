package com.example.business.constants;

/**
 * 异常提示常量
 */
public class ExceptionInfoConstants {

    /**
     * 参数错误：比例值大于100
     */
    public static final String PARAM_PROPORTION_OUT_OF_EXCEPTION = "比例值总和超过100！";

    /**
     * 参数错误：开始时间大于结束时间
     */
    public static final String PARAM_DATE_PARAMS_EXCEPTION = "开始时间不能大于结束时间！";

    /**
     * 参数错误：比例值设置错误
     */
    public static final String PARAM_PROPORTION_EXCEPTION = "比例值设置错误！";

    /**
     * 登录错误：用户不存在
     */
    public static final String USERNAME_NOT_FOUND = "用户不存在！";

    /**
     * 登录错误：密码错误
     */
    public static final String USERNAME_PASSWORD_ERROR = "用户名或密码错误！";

    /**
     * 登录错误：用户已存在
     */
    public static final String USER_ALREADY_EXISTS = "用户已存在";

}
