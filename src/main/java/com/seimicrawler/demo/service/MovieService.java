package com.seimicrawler.demo.service;

import com.seimicrawler.demo.domain.model.MovieModel;
import com.seimicrawler.demo.infra.persistence.sql.MovieMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author     : WindAsMe
 * File       : MovieService.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/MovieService.java
 */
@Service
public class MovieService {

    @Resource
    private MovieMapper mapper;

    public void insertMovieModel(MovieModel model) {
        this.mapper.insertMovieModel(model);
    }

    public List<MovieModel> selectMovieModelAll() {
        return this.mapper.selectMovieModelAll();
    }
}
