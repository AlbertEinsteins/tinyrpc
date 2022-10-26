package com.tinypc.client.handler;

import com.tinypc.serializer.Serializer;
import com.tinyrpc.entity.TPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 封装RpcRequest对象到TPackage对象中的body中
 */
public class TPackageEncoder extends ChannelOutboundHandlerAdapter {

    public Serializer<> serializer;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        super.write(ctx, msg, promise);
        System.out.println(msg);
        TPackage pkg = TPackage.create()
    }
}
