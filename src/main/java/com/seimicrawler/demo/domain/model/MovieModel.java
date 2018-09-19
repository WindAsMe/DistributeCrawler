package com.seimicrawler.demo.domain.model;

/**
 * Author     : WindAsMe
 * File       : MovieModel.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/MovieModel.java
 */
public class MovieModel {

    private int id;
    private String url;
    private String title;
    private String context;
    private String time;


    public MovieModel(String url, String title, String context, String time) {
        this.url = url;
        this.title = title;
        this.context = context;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
