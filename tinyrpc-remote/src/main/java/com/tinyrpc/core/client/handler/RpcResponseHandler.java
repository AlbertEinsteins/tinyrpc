package com.tinyrpc.core.client.handler;

import com.tinyrpc.core.context.BodySerializerContext;
import com.tinyrpc.core.factory.SingletonFactory;
import com.tinyrpc.core.entity.RpcResponse;
import com.tinyrpc.core.entity.TPackage;
import com.tinyrpc.core.entity.enumerate.PackageType;
import com.tinyrpc.core.entity.enumerate.SerializeType;
import io.netty.channel.*;

import java.util.concurrent.CountDownLatch;


public class RpcResponseHandler extends ChannelInboundHandlerAdapter {
    private RpcResponse response;
    private CountDownLatch waitForResponse;

    private static final BodySerializerContext bodySerializerContext;
    static {
        bodySerializerContext = SingletonFactory.getInstance(BodySerializerContext.class);
    }

    public RpcResponseHandler(CountDownLatch waitForResponse, RpcResponse response) {
        this.waitForResponse = waitForResponse;
        this.response = response;
    }

    //获取相应结果
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解码出消息体, 封装到response
        TPackage tPackage = (TPackage) msg;
        if(tPackage.getPackageType() == PackageType.RPC_REQUEST) {
            SerializeType serialType = tPackage.getSerialType();
            // 接收返回值
            RpcResponse response = (RpcResponse) bodySerializerContext.deSerializeBody(tPackage.getBody(), serialType);

            this.response.setResult(response.getResult());
            this.response.setReturnType(response.getReturnType());
            waitForResponse.countDown();
        }
        else {
            ctx.fireChannelRead(msg);
        }
    }

}
