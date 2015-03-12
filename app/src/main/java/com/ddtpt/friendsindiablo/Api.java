package com.ddtpt.friendsindiablo;

import com.google.gson.JsonElement;

import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by e228596 on 2/20/2015.
 */
public interface Api {

    @GET("/api/d3/profile/{account}/hero/{hero}")
    void getHero(@Path("account") String account, @Path("hero") String hero, Callback<JsonElement> callback);

    @GET("/api/d3/profile/{account}/")
    void getAccount(@Path("account") String account, Callback<JsonElement> callback);

    @GET("/api/d3/data/item/{item}")
    void getItem(@Path("item") String item, Callback<JsonElement> callback);

    //@GET("/api/d3/data/")

}
