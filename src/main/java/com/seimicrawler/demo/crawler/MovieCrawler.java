package com.seimicrawler.demo.crawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.seimicrawler.demo.domain.model.MovieModel;
import com.seimicrawler.demo.domain.util.RedisPool;
import com.seimicrawler.demo.service.MovieService;
import org.seimicrawler.xpath.JXDocument;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
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

    @Resource
    private MovieService service;

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
            List<Object> urls = doc.sel("//td[@height='26']/b/a[@class='ulink']/@href");
            List<Object> titles = doc.sel("//td[@height='26']/b/a[@class='ulink']/text()");
            List<Object> contexts = doc.sel("//td[@colspan='2']/text()");
            logger.info("information: {} {} {}", urls.size(), titles.size(), contexts.size());
            int min = Math.min(Math.min(urls.size(), titles.size()), contexts.size());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

            Jedis jedis = RedisPool.getJedis();
            if (jedis == null)
                return;
            for (int i = 0; i < min; i++) {
                String base = "http://www.dytt8.net";
                MovieModel model = new MovieModel(base + urls.get(i).toString(), titles.get(i).toString(), contexts.get(i).toString(), format.format(System.currentTimeMillis()));
                System.out.println("url: " + base + urls.get(i) + " title: " + titles.get(i) + " context: " + contexts.get(i));
                if (jedis.get(urls.get(i).toString()) == null) {
                    service.insertMovieModel(model);
                    jedis.set(urls.get(i).toString(), "1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
