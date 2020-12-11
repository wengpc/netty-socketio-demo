package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setContext("/abc"); //socket服务路径

        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener((SocketIOClient client) -> {
            System.out.println("客户端请求连接。。。");
        });
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                System.out.println("客户端接收信息：" + data.toString());
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
