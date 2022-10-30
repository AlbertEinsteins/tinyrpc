package com.tinypc.client;

import com.tinypc.client.handler.ClientInitializer;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private final String serverHost;
    private final Integer serverPort;

    private static final String DEFAULT_HOST = "127.0.0.1";
    public RpcClient(String host, Integer port) {
        this.serverHost = host;
        this.serverPort = port;
    }
    public RpcClient(Integer port) {
        this(DEFAULT_HOST, port);
    }

    //============= 得到返回值 =====================
    // 拿到和服务器交互的channel
    private Channel channel;

    // 等待读线程将服务器返回的结果写到response的锁
    private CountDownLatch waitForResponse;
    private RpcResponse response;
    // 需要外部注入请求内容
    private RpcRequest request;

    public void start() {
        this.waitForResponse = new CountDownLatch(1);
        this.response = new RpcResponse();
        this.start0();
    }
    private void start0() {
        NioEventLoopGroup main = new NioEventLoopGroup(1);
        Bootstrap clientBoot = new Bootstrap();

        try {
            clientBoot.group(main)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientInitializer(waitForResponse, response));

            // 连接服务器
            // todo: 连接失败，重试操作
            this.channel = clientBoot.connect(serverHost, serverPort).sync().channel();
            logger.info("客户端启动成功");

            //发送请求
            this.send();
            //等待写回的结果
            waitForResponse.await();
            logger.info("得到返回结果");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            main.shutdownGracefully();
            logger.info("服务器关闭");
        }
    }

    private void send() {
        channel.writeAndFlush(this.request);
    }

    public void setRpcRequest(RpcRequest rpcRequest) {
        this.request = rpcRequest;
    }
    public RpcResponse getResponse() {
        start();
        return this.response;
    }
    public RpcResponse getResponse(RpcRequest rpcRequest) {
        // 设置rpc请求，发送报文
        setRpcRequest(rpcRequest);
        return this.getResponse();
    }
}
