package com.example.utils;

import java.util.HashMap;

/**
 * 消息响应体
 */
public class Result extends HashMap<String, Object> {

//---------------------------定义key常量-----------------------------

    public static final String Code_KEY = "code";

    public static final String MSG_KEY = "msg";

    public static final String DATA_KEY = "data";

//---------------------------构造-----------------------------

    public Result(){

    }

    public Result(int code, String msg){
        super.put(Code_KEY ,code);
        super.put(MSG_KEY , msg);
    }

    public Result(int code, String msg, Object data){
        super.put(Code_KEY ,code);
        super.put(MSG_KEY , msg);
        super.put(DATA_KEY, data);
    }

//--------------------------应用------------------------------

    /**
     * 返回带数据的成功信息
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    /**
     * 返回不带数据的成功信息
     * @param msg
     * @return
     */
    public static Result success(String msg) {
        return new Result(200, msg);
    }

    /**
     * 返回带数据且自定义消息的成功信息
     * @param msg
     * @param data
     * @return
     */
    public static Result success(String msg, Object data) {
        return new Result(200,msg,data);
    }

    /**
     * 返回错误信息
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        return new Result(500, msg);
    }

    /**
     * 返回携带错误数据的错误信息
     * @param msg
     * @param data
     * @return
     */
    public static Result error(String msg, Object data) {
        return new Result(500, msg, data);
    }
}
