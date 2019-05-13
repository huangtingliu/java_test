package com.huangtl.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接收者/消费者（consumer）,监听队列消息
 */
public class ReciveTest {

    private final static String queue = "test";

    public static void main1(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();

        Channel channe = conn.createChannel();
        //因为实际中消费者可能先与生产者启动，有可能生产者还没创建队列，所以使用前要确保队列存在，没有则会创建
        channe.queueDeclare(queue,false,false,false,null);
        System.out.println("waiting for messsage.");

        //定义一个消费者，并重写接收消息方法
        Consumer consumer = new DefaultConsumer(channe) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("接收到消息："+message);
            }
        };

        channe.basicConsume(queue,true,consumer);
    }
}
