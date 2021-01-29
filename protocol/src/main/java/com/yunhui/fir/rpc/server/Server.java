package com.yunhui.fir.rpc.server;

import com.yunhui.fir.rpc.common.Codec;
import com.yunhui.fir.rpc.config.ServerConfig;
import com.yunhui.fir.rpc.handler.Handler;

import java.util.Map;

/**
 * @Date : 2021/1/19 3:47 下午
 * @Author : dushaoyun
 */
public interface Server {

    ServerConfig getConfig();

    void open();

    boolean isOpen();

    void close();

    Codec getCodec();

    Map<String, Handler> getHandlerMap();

}
