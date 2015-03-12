package com.ddtpt.friendsindiablo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by e228596 on 2/27/2015.
 */
public class RestTestFragment extends Fragment {
    final static String ENDPOINT = "http://us.battle.net/";
    final static String CAREER = "career";
    final static String CHAR = "character";
    final static String ITEM = "item";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RESTUtils restUtils = new RESTUtils();
        //restUtils.getCareerDetails("killbot-1138");
        //restUtils.getHeroDetails("killbot-1138", 58555113);
        //restUtils.parseItemStats();
    }

}
