package com.tinyrpc.core.server.handler;

import com.tinyrpc.core.context.BodySerializerContext;
import com.tinyrpc.core.entity.RpcRequest;
import com.tinyrpc.core.entity.RpcResponse;
import com.tinyrpc.core.entity.TPackage;
import com.tinyrpc.core.entity.enumerate.PackageType;
import com.tinyrpc.core.entity.enumerate.SerializeType;
import com.tinyrpc.core.skeleton.skeleton.ApplicationContextSkeleton;
import com.tinyrpc.core.skeleton.skeleton.ISkeleton;
import com.tinyrpc.core.factory.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcRequestHandler extends ChannelInboundHandlerAdapter {
    private final BodySerializerContext bodySerializerContext;

    private ISkeleton skeleton;

    public RpcRequestHandler() {
        this.bodySerializerContext = SingletonFactory.getInstance(BodySerializerContext.class);
        this.skeleton = SingletonFactory.getInstance(ApplicationContextSkeleton.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TPackage pkg = (TPackage) msg;
        if(pkg.getPackageType() == PackageType.RPC_REQUEST) {
            SerializeType serializeType = pkg.getSerialType();
            System.out.println(pkg.getBody().length);
            RpcRequest rpcRequest = (RpcRequest) bodySerializerContext.deSerializeBody(pkg.getBody(), serializeType);

            Object result = skeleton.invokeMethod(rpcRequest);
            //将结果传递给下一个handler，将其封装为RpcResponse，返回
            RpcResponse response = new RpcResponse();
            response.setResult(result);
            response.setReturnType(result.getClass());

            //交给下个channel处理
            ctx.writeAndFlush(response);
        }
        else { //这个handler不处理其他类型的报文
            ctx.fireChannelRead(msg);
        }
    }


}
