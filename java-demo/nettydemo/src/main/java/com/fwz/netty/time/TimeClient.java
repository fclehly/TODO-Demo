package com.fwz.netty.time;

import com.fwz.netty.common.AbstractClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TimeClient extends AbstractClient {
    @Override
    public ChannelInitializer createChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("timeClientHandler", new TimeClientHandler());
            }
        };
    }

    public static void main(String[] args) {
        TimeClient timeClient = new TimeClient();
        timeClient.connect();
    }
}
