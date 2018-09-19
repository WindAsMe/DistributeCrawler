package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.seimicrawler.demo.domain.model.JDModel;
import com.seimicrawler.demo.domain.util.RedisPool;
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

@Crawler(name = "JDCrawler", queue = DefaultRedisQueue.class, useUnrepeated = false)
public class JDCrawler extends BaseSeimiCrawler {

    private String base = "https://search.jd.com";
    private int page = 1;

    @Resource
    private JDService service;

    @Override
    public String[] startUrls() {

        return new String[]{"https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=1&s=1&click=0"};
        // https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=1&s=1&click=0
        // https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=3&s=63&click=0
        // https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=5&s=121&click=0
        // https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=7&s=181&click=0
        // .....
    }

    @Override
    public void start(Response response) {
        try {
            String pre = "https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=";
            String s = "&s=";
            String ord = "&click=0";
            int page = 1;
            int commodity = 1;
            // https://search.jd.com/Search?keyword=iphonex&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&bs=1&wq=iphonex&ev=exbrand_Apple%5E&page=61&s=1801&click=0
            for (; page <= 61; page += 2) {
                String url = pre + page + s + commodity + ord;
                Request request = Request.build(url, "getCommodity");
                push(request);
                commodity += 60;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Callback Function
    public void getCommodity(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-img']/a/@href");
            List<Object> prices = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-price']/strong");
            List<Object> titles = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-name p-name-type-2']/a/@title");
            logger.info("information: {} {} {}", urls.size(), prices.size(), titles.size());
            int min = Math.min(Math.min(urls.size(), prices.size()),titles.size());
            // Cache is futile
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
            Jedis jedis = RedisPool.getJedis();
            if (jedis == null)
                return;
            for (int i = 0; i < min; i++) {
                int len = prices.get(i).toString().length();
                String price = getIntegerValue(prices.get(i).toString().substring(len - 20, len - 13));
                System.out.println("url: " + base + urls.get(i) + " price: " + price + " title: " + titles.get(i));
                // Save and mark
                // Connection url
                if (jedis.get(urls.get(i).toString()) == null) {
                    service.insertJDModel(new JDModel(base + urls.get(i).toString(), page, titles.get(i).toString(), price, format.format(System.currentTimeMillis())));
                    jedis.set(urls.get(i).toString(), "1");
                }
            }
            page++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET the number in s
    private String getIntegerValue(String s) {
        StringBuilder builder = new StringBuilder();
        char[] helper = s.toCharArray();
        for (char c : helper) {
            if (c >= '0' && c <= '9')
                builder.append(c);
        }
        return builder.toString();
    }
}