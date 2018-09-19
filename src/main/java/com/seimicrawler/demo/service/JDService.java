package com.seimicrawler.demo.service;

import com.seimicrawler.demo.domain.model.JDModel;
import com.seimicrawler.demo.infra.persistence.sql.JDMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author     : WindAsMe
 * File       : JDService.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/JDService.java
 */
@Service
public class JDService {

    @Resource
    private JDMapper mapper;

    public void insertJDModel(JDModel model) {
        this.mapper.insertJDModel(model);
    }
}
