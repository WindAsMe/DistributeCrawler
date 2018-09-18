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
        return new String[]{"http://www.cnblogs.com/"};
    }

    @Override
    public void start(Response response) {

        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='titlelnk']/@href");
            logger.info("{}", urls.size());
            for (Object s:urls)
                push(Request.build(s.toString(), BasicA::getTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
            logger.info("url:{} {}", response.getUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            //do something
            Jedis jedis = RedisPool.getJedis();
            if (jedis != null && jedis.get(response.getRealUrl()) == null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
                service.insertBasicBatch(new BasicModel(response.getRealUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()").toString(), response.getContent().length(), format.format(System.currentTimeMillis())));
                jedis.set(response.getRealUrl(), "1");
                System.out.println("response.getRealUrl(): " + response.getRealUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}