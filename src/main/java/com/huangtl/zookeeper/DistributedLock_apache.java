package com.huangtl.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class DistributedLock_apache  {

    private static final String zkDir = "/huangtl_zk_apache";
    private ZooKeeper zk = null;

    {
        try {
            zk = new ZooKeeper("127.0.0.1:2181",5000,new MyWatcher());
            Stat stat = zk.exists(zkDir, new MyWatcher());
            System.out.println("stat:"+stat);
            if(null == stat){
                zk.create(zkDir,"root".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    //加锁
    public Node lock(){
        Node node = createNode();
        if(!getLock(node)){
            synchronized (node){
                try {
                    node.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return node;
    }

    //解锁
    public void unlock(Node node){
        try {
            //version -1代表匹配所有节点版本，文档：http://zookeeper.apache.org/doc/r3.4.14/api/index.html
            zk.delete(node.getPath(),-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    //获得锁
    private boolean getLock(Node node) {

        return false;
    }

    private Node createNode(){
        try {
            String path = zk.create(zkDir + "/node", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            return new Node(path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    class MyWatcher implements Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("WatchedEvent 监听："+watchedEvent.toString());
            switch (watchedEvent.getType()){
                case NodeCreated:
                    System.out.println("监听到节点创建事件type：NodeCreated");
                    break;
                case NodeDeleted:
                    System.out.println("监听到节点删除事件type：NodeDeleted");
                    //判断后一个节点存在则解锁该节点
                    break;
                case NodeDataChanged:
                    System.out.println("监听到节点数据改变事件type：NodeDataChanged");
                    break;
                case NodeChildrenChanged:
                    System.out.println("监听到节点改变事件type：NodeChildrenChanged");
                    break;
                default:
                    System.out.println("监听到type:"+watchedEvent.getType());
            }
        }
    }


}
