package com.ddtpt.friendsindiablo;

import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by e228596 on 2/20/2015.
 */
public interface Api {

    @GET("/api/d3/profile/{account}/hero/{hero}/")
    void getHero(Callback<Hero> callback, String account, String hero);

    @GET("/api/d3/profile/{account}/")
    void getAccount(Callback<String> callback, String account);

    @GET("/api/d3/data/item/{item}")
    void getItem(Callback<String> callback, String item);

    //@GET("/api/d3/data/")

}
