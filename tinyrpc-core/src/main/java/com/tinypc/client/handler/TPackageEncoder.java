package com.tinypc.client.handler;

import com.tinypc.factory.SingletonFactory;
import com.tinypc.protocal.ProtocalBuilder;
import com.tinypc.serializer.JDKSerializer;
import com.tinypc.serializer.Serializer;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.TPackage;
import com.tinyrpc.entity.enumerate.PackageType;
import com.tinyrpc.entity.enumerate.SerializeType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 封装RpcRequest对象到TPackage对象中的body中
 */
public class TPackageEncoder extends ChannelOutboundHandlerAdapter {

    private Serializer<RpcRequest> bodySerializer;
    private SerializeType serializeType;

    public TPackageEncoder() {
        this.bodySerializer = SingletonFactory.getInstance(JDKSerializer.class);
        this.serializeType = SerializeType.JDK_SERIALIZE;
    }

    public TPackageEncoder(SerializeType serializeType, Serializer<RpcRequest> serializer) {
        this.bodySerializer = serializer;
        this.serializeType = serializeType;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        super.write(ctx, msg, promise);
        //发送rpc包
        byte[] body = new byte[0];
        if(msg instanceof RpcRequest) {
            body = bodySerializer.serialize((RpcRequest) msg);
        }
        System.out.println(body.length);
        TPackage tPackage = TPackage.create(PackageType.RPC_REQUEST, SerializeType.JDK_SERIALIZE, body);

        ProtocalBuilder protocalBuilder = new ProtocalBuilder();
        ByteBuf buf = ctx.alloc().buffer(1 << 10);
        byte[] packageByte = protocalBuilder.encodePackage(tPackage);
        buf.writeBytes(packageByte);
        ctx.writeAndFlush(buf, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
