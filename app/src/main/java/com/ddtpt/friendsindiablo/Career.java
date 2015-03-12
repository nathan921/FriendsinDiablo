package com.ddtpt.friendsindiablo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e228596 on 2/20/2015.
 */
public class Career {

    private boolean mExists;

    private String mBattleTag;

    private int mParagonLevel;

    private int mParagonLevelHardcore;

    private int mParagonLevelSeason;

    private int mParagonLevelSeasonHardcore;

    private ArrayList<Hero> mHeroes;

    public boolean exists() {
        return mExists;
    }

    public void setExists(boolean exists) {
        mExists = exists;
    }

    public String getBattleTag() {
        return mBattleTag;
    }

    public void setBattleTag(String battleTag) {
        mBattleTag = battleTag;
    }

    public int getParagonLevel() {
        return mParagonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        mParagonLevel = paragonLevel;
    }

    public int getParagonLevelHardcore() {
        return mParagonLevelHardcore;
    }

    public void setParagonLevelHardcore(int paragonLevelHardcore) {
        mParagonLevelHardcore = paragonLevelHardcore;
    }

    public int getParagonLevelSeason() {
        return mParagonLevelSeason;
    }

    public void setParagonLevelSeason(int paragonLevelSeason) {
        mParagonLevelSeason = paragonLevelSeason;
    }

    public int getParagonLevelSeasonHardcore() {
        return mParagonLevelSeasonHardcore;
    }

    public void setParagonLevelSeasonHardcore(int paragonLevelSeasonHardcore) {
        mParagonLevelSeasonHardcore = paragonLevelSeasonHardcore;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        mHeroes = heroes;
    }

    public KillStats getKillStats() {
        return mKillStats;
    }

    public void setKillStats(KillStats killStats) {
        mKillStats = killStats;
    }

    private KillStats mKillStats;


    public Career()  {
        mExists = false;
    }

    public void generateCharInfo(String json) {

    }

    public ArrayList<Hero> getHeroes() {
        return mHeroes;
    }

    public class KillStats {

        private int monsters;
        private int elites;
        private int hardcoreMonsters;

        public KillStats(int monsters, int elites, int hardcoreMonsters) {
            this.monsters = monsters;
            this.elites = elites;
            this.hardcoreMonsters = hardcoreMonsters;
        }
    }
}
