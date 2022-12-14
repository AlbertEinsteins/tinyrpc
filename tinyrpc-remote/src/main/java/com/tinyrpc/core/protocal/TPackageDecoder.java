package com.tinyrpc.core.protocal;

import com.tinyrpc.core.entity.TPackage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * 从序列化数据中提取Tpackage包
 */
public class TPackageDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bufArray = new byte[buf.readableBytes()];
        buf.readBytes(bufArray);
        TPackage tPackage = new ProtocalBuilder().decodeData(bufArray);
        ctx.fireChannelRead(tPackage);
    }
}
