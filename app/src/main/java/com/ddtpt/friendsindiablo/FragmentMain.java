package com.ddtpt.friendsindiablo;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by e228596 on 2/19/2015.
 */
public class FragmentMain extends ListFragment {
    private ArrayList<String> storedCharacters;
    private static final String ENDPOINT = "http://us.battle.net";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("app","flip the dick");

        storedCharacters = new ArrayList<String>();
        storedCharacters.add("Killbot-1136");

        setListAdapter(new CharacterListAdapter(storedCharacters));

        doSomeShit();

    }

    private void doSomeShit() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        Api service = restAdapter.create(Api.class);

        service.getHero(new Callback<Hero>() {
            @Override
            public void success(Hero hero, Response response) {
                Log.v("Character Data: ", response.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("That Shit FAILED", "JEW");
            }
        }, "Killbot-1136", "1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        return v;
    }

    private class CharacterListAdapter extends ArrayAdapter<String> {
        public CharacterListAdapter(ArrayList<String> chars) {
            super(getActivity(), 0, chars);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_character, null);
            }
            String character = getItem(position);

            TextView charNameTextView = (TextView) convertView.findViewById(R.id.char_list_item_name);
            charNameTextView.setText(character);

            return convertView;
        }
    }
}

