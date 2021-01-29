package com.yunhui.fir.rpc.handler;

/**
 * @Date : 2021/1/29 10:50 上午
 * @Author : dushaoyun
 */
public interface Handler {

    String getServiceName();

    Object handle(Object message);

}