package com.ddtpt.friendsindiablo;

import com.squareup.otto.Bus;

/**
 * Created by e228596 on 3/11/2015.
 */
public class BusProvider {
    private static final Bus mBus = new Bus();

    public BusProvider() {

    }

    public static Bus getInstance() {
        return mBus;
    }
}
