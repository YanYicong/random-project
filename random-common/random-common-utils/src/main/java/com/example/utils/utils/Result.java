package com.example.utils.utils;

import com.example.utils.utils.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;

/**
 * 消息响应体
 */
@ApiModel("消息响应体")
public class Result extends HashMap<String, Object> {

//---------------------------定义key常量-----------------------------

    @ApiModelProperty("状态码")
    public static final String Code_KEY = "code";

    @ApiModelProperty("消息")
    public static final String MSG_KEY = "msg";

    @ApiModelProperty("数据")
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
     * 分页查询成功返回
     * @param list
     * @return
     */
    public static TableDataInfo successPageInfo(List<?> list){
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 分页查询失败返回
     * @param msg
     * @return
     */
    public static TableDataInfo errorPageInfo(String msg){
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(500);
        rspData.setMsg(msg);
        return rspData;
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
