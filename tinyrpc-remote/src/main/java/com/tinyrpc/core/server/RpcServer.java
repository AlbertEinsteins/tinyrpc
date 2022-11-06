package com.tinyrpc.core.server;

import com.tinyrpc.core.server.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServer implements Runnable {
    private final Integer port;
    private final String host;

    private static final String DEFAULT_HOST = "127.0.0.1";

    private static final Integer DEFAULT_PORT = 6700;

    @Override
    public void run() {
        this.startServer();
    }

    public RpcServer(String host, Integer port) {
        this.port = port;
        this.host = host;
    }

    public RpcServer() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public RpcServer(Integer port) {
        this(DEFAULT_HOST, port);
    }



    public void startServer() {
        this.start0();
    }

    private void start0() {
        NioEventLoopGroup main = new NioEventLoopGroup(1);
        NioEventLoopGroup sub = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(main, sub)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            ChannelFuture sync = server.bind(port).sync();
            System.out.println("服务器启动...");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            main.shutdownGracefully();
            sub.shutdownGracefully();
            System.out.println("服务器关闭...");
        }
    }
}
