package com.yunhui.fir.rpc.exception;

/**
 * @Date : 2021/1/19 4:05 下午
 * @Author : dushaoyun
 */
public class FirRpcException extends RuntimeException{
    public FirRpcException(String msg) {
        super(msg);
    }

    public FirRpcException(String message, Throwable cause) {
        super(message, cause);
    }

}
