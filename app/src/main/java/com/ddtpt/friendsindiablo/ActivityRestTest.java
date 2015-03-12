package com.ddtpt.friendsindiablo;

import android.app.Fragment;

/**
 * Created by e228596 on 2/27/2015.
 */
public class ActivityRestTest extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RestTestFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_main;
    }
}
