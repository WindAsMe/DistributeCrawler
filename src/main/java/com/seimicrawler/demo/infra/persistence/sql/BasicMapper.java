package com.seimicrawler.demo.infra.persistence.sql;

import com.seimicrawler.demo.domain.BasicModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author     : WindAsMe
 * File       : BasicMapper.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/BasicMapper.java
 */
@Mapper
public interface BasicMapper {

    void insertBasicBatch(@Param("model") BasicModel model);
}
