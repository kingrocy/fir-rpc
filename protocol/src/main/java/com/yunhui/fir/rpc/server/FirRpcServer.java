package com.yunhui.fir.rpc.server;

import com.yunhui.fir.rpc.config.ServerConfig;

/**
 * @Date : 2021/1/19 3:32 下午
 * @Author : dushaoyun
 */
public class FirRpcServer {

    private ServerConfig serverConfig;

    private Server server;


    public FirRpcServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        this.server = new NettyTransportServer(serverConfig);
    }

    public void startServer() {
        if (!server.isOpen()){
            server.open();
        }
    }

}
