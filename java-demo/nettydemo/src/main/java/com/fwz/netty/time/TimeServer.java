package com.fwz.netty.time;

import com.fwz.netty.common.AbstractServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TimeServer extends AbstractServer {
    @Override
    public ChannelInitializer createChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TimeServerHandler());
            }
        };
    }

    public static void main(String[] args) {
        TimeServer timeServer = new TimeServer();
        timeServer.bind();
    }
}
