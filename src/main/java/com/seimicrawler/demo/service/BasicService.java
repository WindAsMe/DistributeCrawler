package com.seimicrawler.demo.service;

import com.seimicrawler.demo.domain.model.BasicModel;
import com.seimicrawler.demo.infra.persistence.sql.BasicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author     : WindAsMe
 * File       : BasicService.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/BasicService.java
 */
@Service
public class BasicService {

    @Resource
    private BasicMapper basicMapper;

    public void insertBasicBatch(BasicModel model) {
        this.basicMapper.insertBasicBatch(model);
    }

}
