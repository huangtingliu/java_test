package com.huangtl.webFetcher;

import org.jsoup.nodes.Node;

import java.util.*;

/**
 * 包含Iterable类，用于遍历 DOM 树
 */
public class WikiNodeIterable  implements Iterable<Node> {

    private Node root;

    public WikiNodeIterable(Node root) {
        this.root = root;
    }

    @Override
    public Iterator<Node> iterator() {
        return new WikiNodeIterator(root);
    }

    /**
     * 内层的类WikiNodeIterator，执行所有实际工作。
     */
    private class WikiNodeIterator implements Iterator<Node> {

        Deque<Node> stack;

        public WikiNodeIterator(Node node) {
            stack = new ArrayDeque<Node>();
            stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * 获取下一个节点
         * 这里用到了迭代DFS实现
         * 该代码与 DFS 的迭代版本几乎相同，但现在分为三个方法：
             1.构造函数初始化栈（使用一个ArrayDeque实现）并将根节点压入这个栈。
             2.isEmpty检查栈是否为空。
             3.next从Node栈中弹出下一个节点，按相反的顺序压入子节点，并返回弹出的Node。如果有人在空Iterator上调用next，则会抛出异常。
         */
        @Override
        public Node next() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }

            Node node = stack.pop();
            List<Node> nodes = new ArrayList<Node>(node.childNodes());
            Collections.reverse(nodes);
            for (Node child: nodes) {
                stack.push(child);
            }
            return node;
        }

    }
}
