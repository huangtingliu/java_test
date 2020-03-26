package com.huangtl.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class DistributedLock implements Watcher {

    private ZooKeeper zk = null;
    public DistributedLock() {
        try {
            zk = new ZooKeeper("",5000,new DistributedLock());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
