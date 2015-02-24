package com.ddtpt.friendsindiablo;

import android.app.Fragment;

/**
 * Created by e228596 on 2/19/2015.
 */
public class ActivityMain extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FragmentCareerList();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_main;
    }

}
