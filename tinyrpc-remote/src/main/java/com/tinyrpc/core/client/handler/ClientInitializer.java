package com.tinyrpc.core.client.handler;

import com.tinyrpc.core.protocal.TPackageDecoder;
import com.tinyrpc.core.protocal.TPackageEncoder;
import com.tinyrpc.core.entity.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.CountDownLatch;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private RpcResponse response;
    private CountDownLatch waitForResponse;

    public ClientInitializer(CountDownLatch waitForResponse, RpcResponse response) {
        this.waitForResponse = waitForResponse;
        this.response = response;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /**
         * 入站由头到尾执行
         * 出站相反
         */
        ChannelPipeline pipeline = ch.pipeline();

        // 添加出站处理, 将RpcRequest封装为TPackage报文
        // 所谓出站就是继承OutboundHandler接口的类
        pipeline.addLast(new TPackageEncoder());

        // 添加入站处理器
        pipeline.addLast(new TPackageDecoder());
        pipeline.addLast(new RpcResponseHandler(waitForResponse, response));
    }
}
