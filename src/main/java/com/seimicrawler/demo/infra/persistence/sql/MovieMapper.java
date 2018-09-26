package com.seimicrawler.demo.infra.persistence.sql;

import com.seimicrawler.demo.domain.model.MovieModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author     : WindAsMe
 * File       : MovieMapper.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/MovieMapper.java
 */
@Mapper
public interface MovieMapper {

    void insertMovieModel(@Param("model") MovieModel model);

    List<MovieModel> selectMovieModelAll();
}
