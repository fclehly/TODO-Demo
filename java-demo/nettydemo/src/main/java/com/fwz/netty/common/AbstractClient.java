package com.fwz.netty.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractClient implements PipelineInitializer {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8080;

    public Channel connect() {
        return connect(createChannelInitializer());
    }

    public Channel connect(ChannelInitializer channelInitializer) {
        return connect(channelInitializer, HOST, PORT);
    }

    public Channel connect(ChannelInitializer channelInitializer, String host, int port) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .group(group)
                    .handler(channelInitializer);
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            return channelFuture.channel();
        } catch (Exception e) {
            log.error("client error: ", e);
            group.shutdownGracefully();
        }
        return null;
    }

    @Override
    public abstract ChannelInitializer createChannelInitializer();
}
