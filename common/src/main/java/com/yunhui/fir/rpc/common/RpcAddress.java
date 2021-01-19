package com.yunhui.fir.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Date : 2021/1/19 3:35 下午
 * @Author : dushaoyun
 */
@Data
@AllArgsConstructor
public class RpcAddress {
    private String ip;
    private int port;
}
