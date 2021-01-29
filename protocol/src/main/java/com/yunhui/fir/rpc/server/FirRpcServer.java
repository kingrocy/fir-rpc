package com.yunhui.fir.rpc.server;

import com.yunhui.fir.rpc.config.ServerConfig;
import com.yunhui.fir.rpc.handler.Handler;
import com.yunhui.fir.rpc.handler.HandlerDelegate;
import com.yunhui.fir.rpc.invoker.ServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Date : 2021/1/19 3:32 下午
 * @Author : dushaoyun
 */
public class FirRpcServer {

    private ServerConfig serverConfig;

    private Server server;

    private CopyOnWriteArrayList<Handler> serviceHandlers = new CopyOnWriteArrayList<>();

    private Map<String, Handler> handlerMap = new ConcurrentHashMap<>();

    public FirRpcServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void startServer() {
        if (!server.isOpen()) {
            this.server = new NettyTransportServer(serverConfig, serviceHandlers);
            server.open();
        }
    }

    public <T> void registerService(Class<T> interfaceClass, T serviceObject) {
        HandlerDelegate handlerDelegate = new HandlerDelegate(new ServiceImpl(serviceObject, interfaceClass));
        serviceHandlers.add(handlerDelegate);
    }


}
