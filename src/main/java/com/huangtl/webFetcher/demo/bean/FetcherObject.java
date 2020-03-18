package com.huangtl.webFetcher.demo.bean;

import com.huangtl.webFetcher.demo.utils.FetcherUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 网站爬虫对象
 */
public class FetcherObject {

    private String url;
    private Document doc;

    public FetcherObject(String url) {
        this.url = url;
        try {
            this.doc = FetcherUtils.getDocByUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据选择器获取元素
     * @param selector
     * @return
     */
    public Elements getElements(String selector){
        return FetcherUtils.getDocElementsBySelector(doc, selector);
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
}
