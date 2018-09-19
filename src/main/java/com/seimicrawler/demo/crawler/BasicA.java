package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.seimicrawler.demo.domain.BasicModel;
import com.seimicrawler.demo.service.BasicService;
import com.seimicrawler.demo.util.RedisPool;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Author     : WindAsMe
 * File       : Basic.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/Basic.java
 */

@Crawler(name = "basic_a",queue = DefaultRedisQueue.class,useUnrepeated = false)
public class BasicA extends BaseSeimiCrawler {

    @Resource
    private BasicService service;

    @Override
    public String[] startUrls() {
        return new String[]{"https://search.jd.com/Search?keyword=iphonex&enc=utf-8&wq=iphonex&pvid=841062b25c2348edb05b90b5f2aaa22f"};
    }

    public String url() {
        StringBuilder builder = new StringBuilder();
        for (String s : startUrls())
            builder.append(s);
        return builder.toString();
    }

    @Override
    public void start(Response response) {

        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-img']/a/@href");
            List<Object> prices = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-price']/strong");
            List<Object> titles = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-name p-name-type-2']/a/@title");
            logger.info("{} {} {}", urls.size(), prices.size(), titles.size());
            for (int i = 0; i < urls.size(); i++) {
                System.out.println("url: " + url() + urls.get(i) + " price: " + prices.get(i).toString().substring(prices.get(i).toString().length() - 20, prices.get(i).toString().length() - 13) + " title: " + titles.get(i));
            }
                //push(Request.build(url() + s.toString(), BasicA::getTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}