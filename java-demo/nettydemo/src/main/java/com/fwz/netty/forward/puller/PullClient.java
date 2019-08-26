package com.fwz.netty.forward.puller;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class PullClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast("httpResponseDecoder", new HttpResponseDecoder());
                            ch.pipeline().addLast("httpRequestEncoder", new HttpRequestEncoder());
                            ch.pipeline().addLast("pullInboundHandler", new PullInboundHandler());
                        }
                    });
            bootstrap.connect("127.0.0.1", 8080).sync();

        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }
}
