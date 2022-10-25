package com.tinypc.server;

import com.tinypc.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServer {
    private Integer port;
    private String host;

    private static final String DEFAULT_HOST = "127.0.0.1";

    public RpcServer(String host, Integer post) {
        this.host = host;
        this.port = port;
    }

    public RpcServer(Integer port) {
        this(DEFAULT_HOST, port);
    }

    public void startServer() {
        this.start0();
    }

    private NioEventLoopGroup main;
    private NioEventLoopGroup sub;
    private ServerBootstrap server;
    private void start0() {
        main = new NioEventLoopGroup(1);
        sub = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(main, sub)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());
    }
}
