package com.ddtpt.friendsindiablo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class FragmentCareerList extends Fragment {
    private ArrayList<String> storedCharacters;
    private static final String ENDPOINT = "http://us.battle.net";
    private static final String FILENAME = "careers.json";
    private Utils mUtils;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: GET STORED CHARACTER LIST AND FILL THE ARRAYLIST
        mUtils = new Utils(getActivity(), FILENAME);

        storedCharacters = new ArrayList<>();
        storedCharacters = mUtils.getCareerList();

        recList = (RecyclerView)getActivity().findViewById(R.id.careerList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        mAdapter = new CharacterAdapter(storedCharacters);
        recList.setAdapter(mAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        return v;
    }

    public class CharacterAdapter extends RecyclerView.Adapter<CareerViewHolder> {
        private ArrayList<String> careerList;

        public CharacterAdapter(ArrayList<String> chars) {
            careerList = chars;
        }

        @Override
        public int getItemCount() {
            return careerList.size();
        }

        @Override
        public void onBindViewHolder(CareerViewHolder cvh, int i) {
            String careerName = careerList.get(i);
            cvh.vName.setText(careerName);
        }

        @Override
        public CareerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.career_card_layout, viewGroup, false);
            return new CareerViewHolder(itemView);
        }



       /* @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_character, null);
            }
            String character = getItem(position);

            TextView charNameTextView = (TextView) convertView.findViewById(R.id.char_list_item_name);
            charNameTextView.setText(character);

            return convertView;
        }*/
    }

    public static class CareerViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;

        public CareerViewHolder(View v) {
            super(v);
            vName = (TextView)v.findViewById(R.id.career_name);
        }
    }

    /*private void doSomeShit() {
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
    }*/
}

