package com.fwz.netty.forward.pusher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushInboundHander extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse httpResponse = ((HttpResponse) msg);
            log.info("push response: {}", httpResponse);
        }
    }
}
