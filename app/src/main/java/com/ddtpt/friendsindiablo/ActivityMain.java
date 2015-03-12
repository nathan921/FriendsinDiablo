package com.ddtpt.friendsindiablo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by e228596 on 2/19/2015.
 */
public class ActivityMain extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected Fragment createFragment() {
        return new FragmentCareerList();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_main;
    }

    @Subscribe
    public void careerDataAvailable(jsonEventResult event) {
        Log.v("Otto", "OTTO WORKED!");
        if (event.getMessage().equals("success")) {
            FragmentCareerList fragment = (FragmentCareerList)this.getFragmentManager().findFragmentById(R.id.fragmentContainer);
            fragment.loadCharactersIntoList();
        } else if (event.getMessage().equals("hero_success")) {
            FragmentCareerList fragment = (FragmentCareerList)this.getFragmentManager().findFragmentById(R.id.fragmentContainer);
            fragment.populateHeroData();
        }
    }

}
