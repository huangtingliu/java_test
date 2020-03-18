package com.huangtl.webFetcher.demo.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FetcherUtils {

    /**
     * 根据网址获取文档对象
     * @param url
     * @return
     * @throws IOException
     */
    public static Document getDocByUrl(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        connect.maxBodySize(0);
        Document doc = connect.get();

        return doc;
    }

    /**
     * 根据选择器在文档中获取元素
     * @param doc
     * @param selector
     * @return
     */
    public static Elements getDocElementsBySelector(Document doc,String selector){
        Elements elements = doc.select(selector);
        return elements;
    }
}
