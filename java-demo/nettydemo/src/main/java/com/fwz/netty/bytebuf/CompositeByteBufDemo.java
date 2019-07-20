package com.fwz.netty.bytebuf;

import io.netty.buffer.*;
import io.netty.util.CharsetUtil;

public class CompositeByteBufDemo {
    public static void main(String[] args) {
        ByteBufAllocator allocator = new UnpooledByteBufAllocator(true);
        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(allocator, true, 5);
        compositeByteBuf.addComponent(Unpooled.copiedBuffer("1234", CharsetUtil.UTF_8));
        compositeByteBuf.addComponent(Unpooled.copiedBuffer("5678", CharsetUtil.UTF_8));
        compositeByteBuf.addComponent(Unpooled.copiedBuffer("9000", CharsetUtil.UTF_8));
        System.out.println(compositeByteBuf);
        for (ByteBuf byteBuf : compositeByteBuf) {
            System.out.println(byteBuf.readByte());
        }
    }
}
