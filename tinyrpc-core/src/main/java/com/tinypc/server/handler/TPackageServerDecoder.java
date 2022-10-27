package com.tinypc.server.handler;

import com.tinypc.protocal.ProtocalBuilder;
import com.tinyrpc.entity.TPackage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TPackageServerDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ByteBuf buf = (ByteBuf) msg;
        byte[] bufArray = new byte[buf.readableBytes()];
        buf.readBytes(bufArray);
        TPackage tPackage = new ProtocalBuilder().decodeData(bufArray);

        System.out.println(tPackage.getVersion());
        System.out.println(tPackage.getPackageType().name());
        System.out.println(tPackage.getSerialType());
        System.out.println(tPackage.getBody().length);
    }
}
