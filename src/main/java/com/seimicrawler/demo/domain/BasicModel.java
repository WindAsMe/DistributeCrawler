package com.seimicrawler.demo.domain;

/**
 * Author     : WindAsMe
 * File       : BasicModel.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/BasicModel.java
 */
public class BasicModel {

    private int id;
    private String url;
    private String title;
    private int content;
    private String time;

    public BasicModel() {
    }

    public BasicModel(String url, String title, int content, String time) {
        this.url = url;
        this.title = title;
        this.content = content;
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

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BasicModel{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
