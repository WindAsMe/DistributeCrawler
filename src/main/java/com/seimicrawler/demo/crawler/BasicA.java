package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Response;
import com.seimicrawler.demo.domain.model.JDModel;
import com.seimicrawler.demo.domain.util.RedisPool;
import com.seimicrawler.demo.service.BasicService;
import com.seimicrawler.demo.service.JDService;
import org.seimicrawler.xpath.JXDocument;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author     : WindAsMe
 * File       : Basic.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/Basic.java
 */

@Crawler(name = "basic_a", queue = DefaultRedisQueue.class, useUnrepeated = false)
public class BasicA extends BaseSeimiCrawler {

    @Resource
    private JDService service;

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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
            Jedis jedis = RedisPool.getJedis();
            List<Object> urls = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-img']/a/@href");
            List<Object> prices = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-price']/strong");
            List<Object> titles = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-name p-name-type-2']/a/@title");
            logger.info("{} {} {}", urls.size(), prices.size(), titles.size());
            // Cache is futile
            if (jedis == null)
                return;
            for (int i = 0; i < urls.size(); i++) {
                int len = prices.get(i).toString().length();
                String price = prices.get(i).toString().substring(len - 20, len - 13);
                System.out.println("url: " + url() + urls.get(i) + " price: " + price + " title: " + titles.get(i));
                // Save and mark
                if (jedis.get(urls.get(i).toString()) == null) {
                    service.insertJDModel(new JDModel(url() + urls.get(i).toString(), titles.get(i).toString(), price, format.format(System.currentTimeMillis())));
                    jedis.set(urls.get(i).toString(), "1");
                }
            }
                //push(Request.build(url() + s.toString(), BasicA::getTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}