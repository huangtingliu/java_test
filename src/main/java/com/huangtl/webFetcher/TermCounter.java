package com.huangtl.webFetcher;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 表示检索词到页面中出现次数的映射
 */
public class TermCounter {

    private Map<String, Integer> map;
    private String label;

    /**
     * 实例变量map包含检索词到计数的映射，并且label标识检索词的来源文档；我们将使用它来存储 URL。
     * @param label
     */
    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap<String, Integer>();
    }

    public void put(String term, int count) {
        map.put(term, count);
    }

    public Integer get(String term) {
        Integer count = map.get(term);
        return count == null ? 0 : count;
    }

    /**
     * 检索词计数
     * @param term
     */
    public void incrementTermCount(String term) {
        put(term, get(term) + 1);
    }

     //===================TermCounter还提供了这些其他方法，来帮助索引网页：================

    /**
     * 处理html文档元素
     * @param paragraphs
     */
    public void processElements(Elements paragraphs) {
        for (Node node: paragraphs) {
            processTree(node);
        }
    }

    /**
     * 遍历处理文本内容
     * @param root
     */
    public void processTree(Node root) {
        for (Node node: new WikiNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    /**
     * 处理文本提取检索词进行计数
     * @param text
     */
    public void processText(String text) {
        String[] array = text.replaceAll("\\pP", " ").
                toLowerCase().
                split("\\s+");

        for (int i=0; i<array.length; i++) {
            String term = array[i];
            incrementTermCount(term);
        }
    }

    public void printCounts() {
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey()+":"+e.getValue());
        }
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Integer> getCounts(){
        return map;
    }
}
