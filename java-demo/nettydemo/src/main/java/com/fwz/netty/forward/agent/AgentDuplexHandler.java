package com.fwz.netty.forward.agent;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentDuplexHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("---- duplex {}", msg);
        if (msg instanceof ByteBuf) {
            ByteBuf buf = ((ByteBuf) msg);
            log.debug("{} {}", buf.getClass().getSimpleName(), buf);
            ctx.fireChannelRead(msg);
        }
    }
}
