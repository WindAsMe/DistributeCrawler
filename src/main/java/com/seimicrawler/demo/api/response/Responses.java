package com.seimicrawler.demo.api.response;

import java.util.HashMap;

/**
 * Author     : WindAsMe
 * File       : Responses.java
 * Time       : Create on 18-9-18
 * Location   : ../Home/SeimiCrawler/Responses.java
 */
public class Responses {

    /**
     * build success response.
     * @return response
     */
    public static Response successResponse() {
        Response response = new Response();

        RespMeta meta = new RespMeta();
        meta.setCode(0);
        response.setMeta(meta);

        return response;
    }

    /**
     * build success response.
     * @param data response data
     * @return success response
     */
    public static Response successResponse(HashMap data) {
        Response response = new Response();

        RespMeta meta = new RespMeta();
        meta.setCode(0);
        response.setMeta(meta);

        response.setData(data);
        return response;
    }

    /**
     * build error response.
     * @param errorMsg error msg
     * @return response
     */
    public static Response errorResponse(String errorMsg) {

        RespMeta meta = new RespMeta();
        meta.setCode(1);
        meta.setErrorMsg(errorMsg);

        Response response = new Response();
        response.setMeta(meta);
        return response;
    }
}
