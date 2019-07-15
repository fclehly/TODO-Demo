package com.fwz.netty.chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatRoomServerChannelHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        for (Channel channel : channels) {
            if (channel == ctx.channel()) {
                channel.writeAndFlush("[you]: " + msg);
            } else {
                channel.writeAndFlush("[" + ctx.channel().remoteAddress() + "]: " + msg);
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " added into room");
        for (Channel channel : channels) {
            channel.writeAndFlush(ctx.channel().remoteAddress() + " added into room");
        }
        channels.add(ctx.channel());
        System.out.println(channels.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " is online");
        for (var channel : channels) {
            channel.writeAndFlush(ctx.channel().remoteAddress() + " is online");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " is offline");
        for (var channel : channels) {
            channel.writeAndFlush(ctx.channel().remoteAddress() + " is offline");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " is leaved");
        for (var channel : channels) {
            channel.writeAndFlush(ctx.channel().remoteAddress() + " is leaved");
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close().sync();
    }
}
