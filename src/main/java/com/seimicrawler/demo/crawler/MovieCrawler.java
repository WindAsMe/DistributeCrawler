package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.seimicrawler.demo.domain.model.JDModel;
import com.seimicrawler.demo.domain.util.RedisPool;
import org.seimicrawler.xpath.JXDocument;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author     : WindAsMe
 * File       : MovieCrawler.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/MovieCrawler.java
 */
@Crawler(name = "MovieCrawler", queue = DefaultRedisQueue.class, useUnrepeated = false)
public class MovieCrawler extends BaseSeimiCrawler {

    private String base = "http://www.dytt8.net";
    @Override
    public String[] startUrls() {

        return new String[]{"http://www.dytt8.net/html/dongman/list_16_1.html"};
        // http://www.dytt8.net/html/dongman/list_16_1.html
        // http://www.dytt8.net/html/dongman/list_16_2.html
        // http://www.dytt8.net/html/dongman/list_16_3.html
        // .....
        // http://www.dytt8.net/html/dongman/list_16_7.html
    }

    @Override
    public void start(Response response) {
        try {
            String pre = "http://www.dytt8.net/html/dongman/list_16_";
            int page = 1;
            String ord = ".html";
            for (;page <= 7; page++) {
                String url = pre + page + ord;
                Request request = Request.build(url, "getAnimation");
                push(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Callback Function
    public void getAnimation(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//td[@height='26']/");
            List<Object> prices = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-price']/strong");
            List<Object> titles = doc.sel("//li[@class='gl-item']/div[@class='gl-i-wrap']/div[@class='p-name p-name-type-2']/a/@title");
            logger.info("information: {} {} {}", urls.size(), prices.size(), titles.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
