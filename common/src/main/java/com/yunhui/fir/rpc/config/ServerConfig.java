package com.yunhui.fir.rpc.config;

import com.yunhui.fir.rpc.common.RpcAddress;
import com.yunhui.fir.rpc.utils.NetUtils;
import lombok.Data;

/**
 * @Date : 2021/1/19 3:33 下午
 * @Author : dushaoyun
 */
@Data
public class ServerConfig {

    private int port;
    private int workerThreadNum;

    public RpcAddress getRpcAddress() {
        String ip = NetUtils.getLocalAddress().getHostAddress();
        return new RpcAddress(ip, port);
    }
}
