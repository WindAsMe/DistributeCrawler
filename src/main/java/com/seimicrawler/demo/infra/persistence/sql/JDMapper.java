package com.seimicrawler.demo.infra.persistence.sql;

import com.seimicrawler.demo.domain.model.JDModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author     : WindAsMe
 * File       : JDMapper.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/JDMapper.java
 */
@Mapper
public interface JDMapper {

    void insertJDModel(@Param("model")JDModel model);

    List<JDModel> selectJDModelAll();
}
