package com.huangtl.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分布式锁实现类
 */
public class DistributedLock_I0Itec {

    private static final String zkDir = "/huangtl_zk";
    private ZkClient client = null;

    public DistributedLock_I0Itec() {
        client = new ZkClient("127.0.0.1:2181",5000,5000);
        if(!client.exists(zkDir)){
            client.createPersistent(zkDir);
        }
    }

    //加锁
    public Node lock(){
        Node node = createNode();
        //如果获取不到锁则等待
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
        System.out.println("删除："+node.getPath());
        boolean delete = client.delete(node.getPath());
    }

    /**
     * 获取锁
     * @param node
     * @return
     */
    private boolean getLock(Node node){
        //获取zookeeper目录下所有子节点并排序no
        List<String> childs = client.getChildren(zkDir).stream().sorted().map(n -> zkDir +"/"+ n).collect(Collectors.toList());
        if(childs.get(0).equals(node.getPath())){
            //代表是第一个
            return true;
        }else{
            //不是第一个节点则需要监听上一个节点，当上一个节点删除后才解锁自己
            String prePath = childs.get(childs.indexOf(node.getPath())-1);
            client.subscribeDataChanges(prePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("上一个节点删除:"+s);
                    synchronized (node) {
                        node.notify();
                    }
                    //删除上一个节点监听事件
                    client.unsubscribeDataChanges(prePath, this);
                }
            });

        }
        return false;
    }

    //创建临时节点
    public Node createNode(){
        String path = client.createEphemeralSequential(zkDir+"/node", "N");
        Node node = new Node(path);
        return node;
    }

}
