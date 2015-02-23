package com.ddtpt.friendsindiablo;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by e228596 on 2/19/2015.
 */
public class ActivityMain extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FragmentMain();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_main;
    }

}
