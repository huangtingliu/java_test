package com.huangtl.webFetcher;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;

/**
 *  DOM 树中深度优先搜索（DFS）的递归和迭代实现
 */
public class DFSNodeExample {

    /**
     * 遍历节点（递归）
     * 深度优先搜索（DFS）
     * 有两种常用的方式来实现 DFS，递归和迭代。递归实现简单优雅：
     * @param node
     */
    private static void recursiveDFS(Node node) {
        if (node instanceof TextNode) {
            System.out.print(node);
        }
        for (Node child: node.childNodes()) {
            recursiveDFS(child);
        }
    }

    /**
     * 遍历节点（迭代）
     * 深度优先搜索（DFS）
     * 它使用ArrayDeque来表示Node对象的栈。
     * @param root
     */
    private static void iterativeDFS(Node root) {
        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node instanceof TextNode) {
                System.out.print(node);
            }

            List<Node> nodes = new ArrayList<Node>(node.childNodes());
            Collections.reverse(nodes);

            for (Node child: nodes) {
                stack.push(child);
            }
        }
    }

}
