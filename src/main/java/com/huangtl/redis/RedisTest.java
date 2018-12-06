package com.huangtl.redis;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisTest {

    public static void main(String[] args) {
        redisString();
        //redisList();
        //redisKeys();
        //redisSet();
        //redisZSet();
//        redisChannel();
//        redisChannel();
    }

    public static Jedis getJedis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        return jedis;
    }

    //redis字符串
    public static void redisString(){

        Jedis jedis = getJedis();
        //设置 redis 字符串数据
        jedis.set("name","huangtl");
        // 获取存储的数据并输出
        System.out.println("redist 存储的字符串为："+jedis.get("name"));
    }

    public static void redisList(){
        Jedis jedis = getJedis();
        //存储到列表
        jedis.lpush("users","张三");
        jedis.lpush("users","李四2");
        jedis.lpush("users","添加到头部");//添加到头部
        jedis.rpush("users","添加到结尾");
        //获取存储的数据

        //String users = jedis.lpop("users");//删除并返回最头部一项
        //System.out.println(users);
        //String last = jedis.rpop("users");
        //System.out.println("删除最后一个："+last);
        Long userLen = jedis.llen("users");
        System.out.println("总共："+userLen+"条记录");
        List<String> list = jedis.lrange("users",0,userLen);
        for (String s : list) {
            System.out.println("列表项："+s);
        }
    }

    public static void redisKeys(){
        System.out.println("获取redis的key:");
        Jedis jedis = getJedis();
        Set<String> keys = jedis.keys("*");

        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
    }

    //无序集合
    public static void redisSet(){
        Jedis jedis = getJedis();
        jedis.sadd("setkey","setvalue1");
        jedis.sadd("setkey","setvalue2");
        Set<String> setvalue = jedis.smembers("setkey");
        Iterator<String> iterator = setvalue.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
    }

    //有序集合
    public static void redisZSet(){
        Jedis jedis = getJedis();
        jedis.zadd("zsetkey",3,"m1");
        jedis.zadd("zsetkey",5,"m2");
        jedis.zadd("zsetkey",1,"m3");
        Set<String> setvalue = jedis.zrange("zsetkey",0,100);
        Iterator<String> iterator = setvalue.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
    }

    //发布/订阅
    public static void redisChannel(){
        Jedis jedis = getJedis();

        JedisPubSub a = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("onMessage channel:"+channel+";message:"+message);
                super.onMessage(channel, message);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println("onPMessage");
                super.onPMessage(pattern, channel, message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("onSubscribe");
                super.onSubscribe(channel, subscribedChannels);
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                System.out.println("onUnsubscribe");
                super.onUnsubscribe(channel, subscribedChannels);
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
                System.out.println("onPUnsubscribe");
                super.onPUnsubscribe(pattern, subscribedChannels);
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                System.out.println("onPSubscribe");
                super.onPSubscribe(pattern, subscribedChannels);
            }

            @Override
            public void onPong(String pattern) {
                System.out.println("onPong");
                super.onPong(pattern);
            }

            @Override
            public void unsubscribe() {
                System.out.println("unsubscribe");
                super.unsubscribe();
            }

            @Override
            public void unsubscribe(String... channels) {
                System.out.println("unsubscribe ... channels");
                super.unsubscribe(channels);
            }

            @Override
            public void subscribe(String... channels) {
                System.out.println("subscribe");
                super.subscribe(channels);
            }

            @Override
            public void psubscribe(String... patterns) {
                System.out.println("psubscribe");
                super.psubscribe(patterns);
            }

            @Override
            public void punsubscribe() {
                System.out.println("");
                super.punsubscribe();
            }

            @Override
            public void punsubscribe(String... patterns) {
                System.out.println("punsubscribe");
                super.punsubscribe(patterns);
            }

            @Override
            public void ping() {
                System.out.println("ping");
                super.ping();
            }

            @Override
            public boolean isSubscribed() {
                System.out.println("isSubscribed");
                return super.isSubscribed();
            }

            @Override
            public void proceedWithPatterns(Client client, String... patterns) {
                System.out.println("proceedWithPatterns");
                super.proceedWithPatterns(client, patterns);
            }

            @Override
            public void proceed(Client client, String... channels) {
                System.out.println("proceed");
                super.proceed(client, channels);
            }

            @Override
            public int getSubscribedChannels() {
                System.out.println("getSubscribedChannels");
                return super.getSubscribedChannels();
            }
        };
        System.out.println("jedis1");
        jedis.subscribe(a,"redisTesChannel");
        //Jedis jedis1 = getJedis();
        //jedis1.subscribe(a,"redisTesChannel");
    }
}
