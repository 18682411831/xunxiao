package com.gbm.mgb.core;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.message=msg;
        this.data=data;
    }

//新增 静态 返回方法  ---------李兵--------
    public static Result build(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result build(int code, String msg) {
        return new Result(code, msg, null);
    }
    public static Result build(int code, Object data) {
        return new Result(code , null, data);
    }
    public static Result build(ResultCode code, Object data) {
        return new Result(Integer.parseInt(code.toString()) , null, data);
    }

    public Result(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok() {
        return new Result(null);
    }
    // ---------------------结束------------------

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }
    public Result setCode(int code) {
        this.code = code;
        return this;
    }
    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return com.alibaba.fastjson.JSON.toJSONString(this);
    }
}
