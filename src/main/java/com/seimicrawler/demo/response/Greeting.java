package com.seimicrawler.demo.response;

/**
 * Author     : WindAsMe
 * File       : Greeting.java
 * Time       : Create on 18-9-18
 * Location   : ../Home/SeimiCrawler/Greeting.java
 */
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
