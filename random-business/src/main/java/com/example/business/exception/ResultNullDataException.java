package com.example.business.exception;

/**
 * 空返回值异常
 */
public class ResultNullDataException extends Exception{

    public ResultNullDataException() {

    }

    public ResultNullDataException(String message) {
        super(message);
    }
}
