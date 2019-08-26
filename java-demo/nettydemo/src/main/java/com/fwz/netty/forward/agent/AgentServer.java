package com.fwz.netty.forward.agent;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .channel(NioServerSocketChannel.class)
                    .group(bossGroup, workerGroup)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("duplexHandler", new AgentDuplexHandler());
                            pipeline.addLast("httpDecoder", new HttpRequestDecoder());
                            pipeline.addLast("httpEncoder", new HttpResponseEncoder());
                            pipeline.addLast("httpAggregator", new HttpObjectAggregator(1024));
                            pipeline.addLast("inboundHandler", new AgentChannelInboundHandler());
                            pipeline.addLast("outboundHandler", new AgentChannelOutboundHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024);
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("agent server error: ", e);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
