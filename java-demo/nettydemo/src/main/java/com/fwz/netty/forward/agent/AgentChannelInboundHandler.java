package com.fwz.netty.forward.agent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentChannelInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = ((FullHttpRequest) msg);
            log.info("full http request: {}", httpRequest);
            byte[] content = new byte[httpRequest.content().readableBytes()];
            for (int i = 0; i < httpRequest.content().readableBytes(); i++) {
                content[i] = httpRequest.content().getByte(i);
            }
            log.info("content: [{}]", new String(content));
        } else if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = ((HttpRequest) msg);
            log.info("http request: {}", httpRequest);
        }
    }
}
