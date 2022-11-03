package com.tinyrpc.core.protocal;

import com.tinyrpc.core.context.BodySerializerContext;
import com.tinyrpc.core.entity.TPackage;
import com.tinyrpc.core.entity.enumerate.PackageType;
import com.tinyrpc.core.entity.enumerate.SerializeType;
import com.tinyrpc.core.factory.SingletonFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 封装RpcRequest对象封装到TPackage
 */
public class TPackageEncoder extends ChannelOutboundHandlerAdapter {

    private final BodySerializerContext bodySerializerContext;

    public TPackageEncoder() {
        this.bodySerializerContext = SingletonFactory.getInstance(BodySerializerContext.class);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        byte[] body;
        TPackage tPackage;

        SerializeType serializeType = SerializeType.JDK_SERIALIZE;
        body = bodySerializerContext.serializeBody(msg, serializeType);
        System.out.println("client body length: " + body.length);

        tPackage = TPackage.create(PackageType.RPC_REQUEST, serializeType, body);

        ProtocalBuilder protocalBuilder = new ProtocalBuilder();
        ByteBuf buf = ctx.alloc().buffer(1 << 10);
        byte[] packageByte = protocalBuilder.encodePackage(tPackage);
        buf.writeBytes(packageByte);
        ctx.writeAndFlush(buf, promise);        //将报文写出
    }

}
