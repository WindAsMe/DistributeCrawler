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
        return new String[]{"https://s.taobao.com/search?spm=a217h.9580640.831011.12.376a25aax6nlmn&q=iphoneX&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20171213&ie=utf8&app=detailproduct&through=1"};
    }

    @Override
    public void start(Response response) {

        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//div[@id=\"item.g-clearfix\"]");
            logger.info("{}", urls.size());
            System.out.println("urls: " + urls.toString());
//            for (Object s:urls)
//                push(Request.build(s.toString(), BasicA::getTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
            logger.info("url:{} {}", response.getUrl(), doc.sel("//a/@p/text()|//a/@span/text()"));
            //do something
            Jedis jedis = RedisPool.getJedis();
            if (jedis != null && jedis.get(response.getRealUrl()) == null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
                service.insertBasicBatch(new BasicModel(response.getRealUrl(), doc.sel("/href").toString(), response.getContent().length(), format.format(System.currentTimeMillis())));
                jedis.set(response.getRealUrl(), "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}