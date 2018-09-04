package com.huangtl.webFetcher;

import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        //String url = "http://en.wikipedia.org/wiki/Java_(programming_language)";
        String url = "http://www.huangtl.com/";
        WikiFetcher wf = new WikiFetcher();
        //Elements paragraphs = wf.fetchWikipedia(url,"#mw-content-text p");
        Elements paragraphs = wf.fetchWikipedia(url,"#index-list");
        //Elements paragraphs = wf.fetchWikipedia(url);

        /**
         * 打印检索词与出现的次数
         */
        TermCounter counter = new TermCounter(url);
        counter.processElements(paragraphs);
        counter.printCounts();
    }
}
