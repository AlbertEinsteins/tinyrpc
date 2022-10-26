package com.tinypc.server.handler;

import com.tinyrpc.entity.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String data = new String(bytes);
        System.out.println("[服务器收到]：" + data);

        ByteBuf write = ctx.alloc().buffer();
        String out = "[服务器收到并发出消息]：" + data;
        write.writeBytes(out.getBytes());
        ctx.writeAndFlush(write);
    }
}
