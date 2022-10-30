package com.tinypc.server.handler;

import com.tinypc.context.BodySerializerContext;
import com.tinypc.core.skeleton.ISkeleton;
import com.tinypc.core.skeleton.ReflectionSkeleton;
import com.tinypc.factory.SingletonFactory;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.RpcResponse;
import com.tinyrpc.entity.TPackage;
import com.tinyrpc.entity.enumerate.PackageType;
import com.tinyrpc.entity.enumerate.SerializeType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcRequestHandler extends ChannelInboundHandlerAdapter {
    private final BodySerializerContext bodySerializerContext;

    private ISkeleton skeleton;

    public RpcRequestHandler() {
        this.bodySerializerContext = SingletonFactory.getInstance(BodySerializerContext.class);
        this.skeleton = SingletonFactory.getInstance(ReflectionSkeleton.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TPackage pkg = (TPackage) msg;
        if(pkg.getPackageType() == PackageType.RPC_REQUEST) {
            //todo
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
        else { //这个handler不处理当前类型的报文
            ctx.fireChannelRead(msg);
        }
    }


}
