package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;

/**
 * Author     : WindAsMe
 * File       : Basic.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/Basic.java
 */

@Crawler(name = "basic_a",queue = DefaultRedisQueue.class,useUnrepeated = false)
public class BasicA extends BaseSeimiCrawler {

    @Override
    public String[] startUrls() {
        return new String[]{"http://www.cnblogs.com/"};
    }

    @Override
    public void start(Response response) {

        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='titlelnk']/@href");
            logger.info("{}", urls.size());
            for (Object s:urls){
                push(Request.build(s.toString(), BasicA::getTitle));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
            System.out.println("url: " + response.getUrl() + " content: " + response.getContent().length() + " title: " + doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            logger.info("url:{} {}", response.getUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}