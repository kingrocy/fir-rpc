package com.yunhui.fir.rpc.server;

import com.yunhui.fir.rpc.common.Codec;
import com.yunhui.fir.rpc.common.DefaultCodec;
import com.yunhui.fir.rpc.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date : 2021/1/19 4:06 下午
 * @Author : dushaoyun
 */
@Slf4j
public class NettyTransportServer implements Server {

    private io.netty.channel.Channel serverChannel;

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    private ServerConfig serverConfig;

    public NettyTransportServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public ServerConfig getConfig() {
        return serverConfig;
    }

    @Override
    public void open() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        log.info("server start...");
                        ch.pipeline().addLast("decoder", new ProtobufVarint32FrameDecoder());
                        ch.pipeline().addLast("encoder", new ProtobufVarint32LengthFieldPrepender());
                        ch.pipeline().addLast("handler", new ServerChannelHandler());
                    }
                });
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        ChannelFuture f = serverBootstrap.bind(getConfig().getPort()).syncUninterruptibly();
        log.info("bind port:{} success", getConfig().getPort());
        serverChannel = f.channel();
    }

    @Override
    public boolean isOpen() {
        return serverChannel != null && bossGroup != null && workerGroup != null;
    }

    @Override
    public void close() {
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
            bossGroup = null;
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            workerGroup = null;
        }
    }

    @Override
    public Codec getCodec() {
        return new DefaultCodec();
    }
}
