package com.yunhui.fir.rpc.server;

import com.yunhui.fir.rpc.common.Codec;
import com.yunhui.fir.rpc.exception.FirRpcException;
import com.yunhui.fir.rpc.handler.Handler;
import com.yunhui.fir.rpc.proto.Request;
import com.yunhui.fir.rpc.proto.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * @Date : 2021/1/19 4:12 下午
 * @Author : dushaoyun
 */
public class ServerChannelHandler extends ChannelDuplexHandler {

    private Map<String, Handler> handlerMap;

    private Codec codec;

    public ServerChannelHandler(Server server) {
        this.handlerMap = server.getHandlerMap();
        this.codec = server.getCodec();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = decodeRequest(msg);
        Response response = (Response) handlerMap.get(request.getInterfaceName()).handle(request);
        response.setRequestId(request.getRequestId());
        sendResponse(ctx, response);
    }

    private Request decodeRequest(Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        byteBuf.release();
        Object obj = codec.decode(data, Codec.DataTypeEnum.REQUEST);
        if (!(obj instanceof Request)) {
            throw new FirRpcException(
                    "ServerChannelHandler: unsupported message type when decode: " + obj.getClass());
        }
        return (Request) obj;
    }

    private ChannelFuture sendResponse(ChannelHandlerContext ctx, Response response) {
        try {
            byte[] msg = codec.encode(response);
            ByteBuf byteBuf = ctx.channel().alloc().heapBuffer();
            byteBuf.writeBytes(msg);
            if (ctx.channel().isActive()) {
                return ctx.channel().writeAndFlush(byteBuf).sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
