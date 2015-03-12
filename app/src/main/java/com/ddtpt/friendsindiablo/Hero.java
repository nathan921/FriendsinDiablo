package com.ddtpt.friendsindiablo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by e228596 on 3/5/2015.
 */
public class Hero {


    String paragonLevel;
    boolean seasonal;
    String name;
    int id;
    int level;
    boolean hardcore;
    int gender;
    boolean dead;

    HeroStats mHeroStats;
    ArrayList<Item> mItems;
    ArrayList<Skill> mSkills;

    @SerializedName("class")
    String hero_class;

    @SerializedName("last-updated")
    int last_updated;


    public Hero() {

    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        mSkills = skills;
    }

    public HeroStats getHeroStats() {
        return mHeroStats;
    }

    public void setHeroStats(HeroStats heroStats) {
        mHeroStats = heroStats;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<Item> items) {
        mItems = items;
    }

    public String getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(String paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getHero_class() {
        return hero_class;
    }

    public void setHero_class(String hero_class) {
        this.hero_class = hero_class;
    }

    public int getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(int last_updated) {
        this.last_updated = last_updated;
    }
}
