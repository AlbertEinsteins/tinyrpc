package com.tinypc.protocal;

import com.tinypc.context.BodySerializerContext;
import com.tinypc.factory.SingletonFactory;
import com.tinypc.serializer.JDKSerializer;
import com.tinypc.serializer.Serializer;
import com.tinypc.server.handler.RpcRequestHandler;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.RpcResponse;
import com.tinyrpc.entity.TPackage;
import com.tinyrpc.entity.enumerate.PackageType;
import com.tinyrpc.entity.enumerate.SerializeType;
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
