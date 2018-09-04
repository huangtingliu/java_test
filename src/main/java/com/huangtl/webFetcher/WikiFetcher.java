package com.huangtl.webFetcher;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 使用jsoup从维基百科下载页面
 *
     编写 Web 爬虫时，很容易下载太多页面，这可能会违反你要下载的服务器的服务条款。为了帮助你避免这种情况，我提供了一个WikiFetcher类，它可以做两件事情：
     它封装了我们在上一章中介绍的代码，用于从维基百科下载页面，解析 HTML 以及选择内容文本。
     它测量请求之间的时间，如果我们在请求之间没有足够的时间，它将休眠直到经过了合理的间隔。默认情况下，间隔为1秒。
 */
public class WikiFetcher {

    private long lastRequestTime = -1;
    private long minInterval = 1000;

    /**
     * 默认
     * @param url
     * @return
     * @throws IOException
     */
    public Elements fetchWikipedia(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document doc = connect.get();

        Element content = doc.getElementById("mw-content-text");
        Elements paragraphs = content.select("p");//段落
        return paragraphs;
    }

    /**
     * 根据选择器获取html
     * @param url
     * @param selector
     * @return
     * @throws IOException
     */
    public Elements fetchWikipedia(String url,String selector) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document doc = connect.get();
        Elements elements = doc.select(selector);
        return elements;
    }

    /**
     * 检查自上次请求以来的时间，如果经过的时间小于minInterval（毫秒），则休眠。
     */
    private void sleepIfNeeded() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = lastRequestTime + minInterval;
            if (currentTime < nextRequestTime) {
                try {
                    Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException e) {
                    System.err.println(
                            "Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }
}
