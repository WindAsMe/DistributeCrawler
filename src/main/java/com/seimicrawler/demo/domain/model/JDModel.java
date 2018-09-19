package com.seimicrawler.demo.domain.model;

/**
 * Author     : WindAsMe
 * File       : JDModel.java
 * Time       : Create on 18-9-19
 * Location   : ../Home/SeimiCrawler/JDModel.java
 */
public class JDModel {

    private int id;
    private String url;
    private String title;
    private String price;
    private String time;

    public JDModel(String url, String title, String price, String time) {
        this.url = url;
        this.title = title;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "JDModel{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
