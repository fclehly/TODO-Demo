package com.fwz.netty.forward.pusher;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class PushClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast("httpResponseDecoder", new HttpResponseDecoder());
                            ch.pipeline().addLast("httpRequestEncoder", new HttpRequestEncoder());
                            ch.pipeline().addLast("pushInboundHandler", new PushInboundHander());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, "http://127.0.0.1/stream",
                    Unpooled.copiedBuffer("init\r\n".getBytes())));
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if ("quit;".equalsIgnoreCase(input)) {
                    log.info("quit!");
                    System.exit(1);
                    break;
                }
                DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                        HttpVersion.HTTP_1_1, HttpMethod.POST, "http://127.0.0.1/stream",
                        Unpooled.copiedBuffer((input + "\r\n").getBytes()));
                log.info("{}", request);
                channel.writeAndFlush(request);
            }
        } catch (Exception e) {
            log.info("push client error: ", e);
            group.shutdownGracefully();
        }
    }
}
