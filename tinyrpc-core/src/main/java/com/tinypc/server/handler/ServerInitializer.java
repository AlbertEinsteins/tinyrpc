package com.tinypc.server.handler;

import com.tinypc.protocal.TPackageDecoder;
import com.tinypc.protocal.TPackageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //添加出站处理器
        pipeline.addLast(new TPackageEncoder());

        // 添加入站处理器
        pipeline.addLast(new TPackageDecoder());
        pipeline.addLast(new RpcRequestHandler());
        pipeline.addLast(new ExceptionHandler());
    }
}
