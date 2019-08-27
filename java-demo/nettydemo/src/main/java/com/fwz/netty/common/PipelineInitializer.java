package com.fwz.netty.common;

import io.netty.channel.ChannelInitializer;

public interface PipelineInitializer {
    ChannelInitializer createChannelInitializer();
}
