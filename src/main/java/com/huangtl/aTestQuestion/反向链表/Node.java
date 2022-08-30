package com.huangtl.aTestQuestion.反向链表;

/**
 * @Description
 * @Author huangtl
 * @Date 2021/12/27 11:34
 * @Since
 **/
public class Node {
    private int val;
    private Node next;

    public Node(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "{" +
                  val +
                ", " + (next!=null?next:"") +
                '}';
    }
}
