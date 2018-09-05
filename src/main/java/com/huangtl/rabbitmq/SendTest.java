package com.huangtl.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布消息测试
 */
public class SendTest {

    private final static String queue = "test";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();

        //创建一个通道
        Channel channel = conn.createChannel();

        //定义一个队列queue，我们可以向队列发布消息:
        //声明一个队列是等幂的(只会在不存在的时候创建)，消息是一个字节数组，你可以发送任何内容
        channel.queueDeclare(queue,false,false,false,null);

        String message = "hello world";
        channel.basicPublish("",queue,null,message.getBytes());
        System.out.println("sent '"+message+"'");

        //关闭通道和连接
        channel.close();
        conn.close();

    }
}
