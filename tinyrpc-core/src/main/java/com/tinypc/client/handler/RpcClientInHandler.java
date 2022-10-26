package com.tinypc.client.handler;

import com.tinyrpc.entity.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.util.concurrent.CountDownLatch;


public class RpcClientInHandler extends ChannelInboundHandlerAdapter {
    private RpcResponse response;
    private CountDownLatch waitForResponse;
    public RpcClientInHandler(CountDownLatch waitForResponse, RpcResponse response) {
        this.waitForResponse = waitForResponse;
        this.response = response;
    }

    //获取相应结果
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解码出消息体, 封装到response
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        waitForResponse.countDown();
    }

}
