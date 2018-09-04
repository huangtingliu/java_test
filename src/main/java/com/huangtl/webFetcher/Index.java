package com.huangtl.webFetcher;

import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 索引器
 * 保存检索词和出现该检索词的页面集合的对应关系
 */
public class Index {

    /**
     * 实例变量index是每个检索词到一组TermCounter对象的映射。每个TermCounter表示检索词出现的页面。
     * 检索词 对应 出现该检索词的页面集合
     */
    private Map<String, Set<TermCounter>> index =
            new HashMap<String, Set<TermCounter>>();

    /**
     * 添加检索词的页面集合
     * @param term
     * @param tc
     */
    public void add(String term, TermCounter tc) {
        Set<TermCounter> set = get(term);

        // if we're seeing a term for the first time, make a new Set
        if (set == null) {
            set = new HashSet<TermCounter>();
            index.put(term, set);
        }
        // otherwise we can modify an existing Set
        set.add(tc);
    }

    public Set<TermCounter> get(String term) {
        return index.get(term);
    }

    /**
     * printIndex方法展示了如何解压缩此数据结构：
     * 外层循环遍历检索词。内层循环迭代TermCounter对象。
     */
    public void printIndex() {
        // loop through the search terms
        for (String term: keySet()) {
            System.out.println(term);

            // for each term, print pages where it appears and frequencies
            Set<TermCounter> tcs = get(term);
            for (TermCounter tc: tcs) {
                Integer count = tc.get(term);
                System.out.println("    " + tc.getLabel() + " " + count);
            }
        }
    }

    private Set<String> keySet() {
        return index.keySet();
    }

    /**
     * 将一个页面的检索词添加进来
     * @param url
     * @param paragraphs
     */
    public void indexPage(String url, Elements paragraphs) {
        // 生成一个 TermCounter 并统计段落中的检索词
        TermCounter counter = new TermCounter(url);
        counter.processElements(paragraphs);
        // 对于 TermCounter 中的每个检索词，将 TermCounter 添加到索引
        Map<String, Integer> counts = counter.getCounts();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String key = entry.getKey();
            if(index.containsKey(key)){
                index.get(key).add(counter);
            }else{
                Set<TermCounter> counterSet = new HashSet<>();
                counterSet.add(counter);
                index.put(key,counterSet);
            }
        }

    }
}
