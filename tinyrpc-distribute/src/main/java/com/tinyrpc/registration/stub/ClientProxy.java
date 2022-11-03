package com.tinyrpc.registration.stub;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tinyrpc.core.client.RpcClient;
import com.tinyrpc.core.entity.RpcRequest;
import com.tinyrpc.core.entity.RpcResponse;

import java.util.concurrent.*;

public class ClientProxy {
    // 通过线程池调用
    private final ExecutorService clientPool;

    public ClientProxy() {
        this.clientPool = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.tinyrpc.registration.stub [clinet proxy]");
                return t;
            };
        });
    }

    public RpcResponse getRpcResponse(RpcRequest request, Instance instance) {
        RpcTask rpcTask = new RpcTask(request, instance.getIp(), instance.getPort());

        Future<RpcResponse> responseFuture = clientPool.submit(rpcTask);
        try {
            return responseFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static class RpcTask implements Callable<RpcResponse> {
        private final RpcRequest rpcRequest;

        private final String serverHost;
        private final Integer serverPort;

        public RpcTask(RpcRequest request, String serverHost, Integer serverPort) {
            this.rpcRequest = request;
            this.serverHost = serverHost;
            this.serverPort = serverPort;
        }

        @Override
        public RpcResponse call() throws Exception {
            RpcClient rpcClient = new RpcClient(serverHost, serverPort);
            rpcClient.setRpcRequest(rpcRequest);
            return rpcClient.getResponse();
        }
    }

}
