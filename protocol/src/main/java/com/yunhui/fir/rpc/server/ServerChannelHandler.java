package com.yunhui.fir.rpc.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Date : 2021/1/19 4:12 下午
 * @Author : dushaoyun
 */
public class ServerChannelHandler extends ChannelDuplexHandler {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
