package com.ddtpt.friendsindiablo;

import java.util.List;

/**
 * Created by e228596 on 2/20/2015.
 */
public class Hero {

    private List<HeroStats> mStats;

    public Hero()  {

    }

    public List<HeroStats> getStats() {
        return mStats;
    }

    public void setStats(List<HeroStats> stats) {
        mStats = stats;
    }
}
