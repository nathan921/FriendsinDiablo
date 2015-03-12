package com.ddtpt.friendsindiablo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by e228596 on 2/27/2015.
 */
public class RESTUtils {
    final static String ACCOUNT_ENDPOINT = "http://us.battle.net/";
    final static String ITEM_ENDPOINT = "http://us.media.blizzard.com/";

    private Api mService;
    private Gson mGson;
    private RestAdapter mRestAdapter;


    public RESTUtils() {
        mGson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(ACCOUNT_ENDPOINT)
                .setConverter(new GsonConverter(mGson))
                .build();

        mService = mRestAdapter.create(Api.class);

    }
    public Career getCareerDetails(String user)  {
        final Career career = new Career();

        mService.getAccount(user, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement json, Response response) {
                JsonObject obj = json.getAsJsonObject();
                if (obj.has("code")) {
                    String reason = json.getAsJsonObject().get("code").getAsString();
                    BusProvider.getInstance().post(new jsonEventResult(reason));
                }
                else {
                    Type HeroArray = new TypeToken<List<Hero>>() {
                    }.getType();

                    String careerJson = mGson.toJson(json);

                    JsonParser parser = new JsonParser();
                    JsonObject object = parser.parse(careerJson).getAsJsonObject();
                    career.setExists(true);
                    career.setBattleTag(mGson.fromJson(object.get("battleTag"), String.class));
                    career.setParagonLevel(mGson.fromJson(object.get("paragonLevel"), Integer.class));
                    career.setParagonLevelHardcore(mGson.fromJson(object.get("paragonLevelHardcore"), Integer.class));
                    career.setParagonLevelSeason(mGson.fromJson(object.get("paragonLevelSeason"), Integer.class));
                    career.setParagonLevelSeasonHardcore(mGson.fromJson(object.get("paragonLevelSeasonHardcore"), Integer.class));
                    career.setKillStats(mGson.fromJson(object.get("kills"), Career.KillStats.class));

                    ArrayList<Hero> heroList = mGson.fromJson(object.get("heroes"), HeroArray);
                    career.setHeroes(heroList);
                    Log.v("REST", json.toString());

                    BusProvider.getInstance().post(new jsonEventResult("success"));
                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.v("REST", "FAILED JEW");
                Log.v("REST", error.toString());
            }
        });

        return career;
    }

    public Hero getHeroDetails(String careerName, int heroId) {
        Hero hero = new Hero();
        mService.getHero(careerName, String.valueOf(heroId), new Callback<JsonElement>() {
            @Override
            public void success(JsonElement hero, Response response) {
                Gson builder = new GsonBuilder()
                        .registerTypeAdapter(HeroStats.class, new statsDeserializer())
                        .create();
                HeroStats stats = builder.fromJson(hero, HeroStats.class);

                //TODO: Stats are never applied to anything
                ArrayList<Item> items;
                ArrayList<Skill> skills;
                JsonObject object = hero.getAsJsonObject().get("items").getAsJsonObject();
                JsonObject skills_object = hero.getAsJsonObject().get("skills").getAsJsonObject();

                items = parseItemInfo(object);
                skills = parseSkillInfo(skills_object);

                Hero h = new Hero();
                h.setItems(items);
                h.setSkills(skills);
                h.setHeroStats(stats);

                Log.v("REST", hero.toString());

                BusProvider.getInstance().post(new jsonEventResult("hero_success"));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("REST", retrofitError.toString());
            }
        });
        return hero;

    }

    private ArrayList<Item> parseItemInfo(JsonElement element) {
        ArrayList<Item> itemList = new ArrayList<>();
        String[] slotList = {"head", "torso", "feet", "hands", "shoulders", "legs", "bracers", "mainHand", "offHand", "waist", "rightFinger", "leftFinger", "neck" };

        for (int i = 0; i < slotList.length; i++) {
            JsonElement json = element.getAsJsonObject().get(slotList[i]);
            if (json != null)  {
                Item singleItem = new Item();
                singleItem.setId(json.getAsJsonObject().get("id").getAsString());
                singleItem.setName(json.getAsJsonObject().get("name").getAsString());
                singleItem.setSlot(slotList[i]);
                singleItem.setDisplayColor(json.getAsJsonObject().get("displayColor").getAsString());
                singleItem.setIcon(json.getAsJsonObject().get("icon").getAsString());

                itemList.add(singleItem);
            }
        }

        return itemList;
    }

    private ArrayList<Skill> parseSkillInfo(JsonObject element) {
        ArrayList<Skill> skillList = new ArrayList<>();
        JsonArray activeSkills = element.getAsJsonArray("active");
        JsonArray passiveSkills = element.getAsJsonArray("passive");

        for (int i = 0; i < activeSkills.size(); i++) {
            Skill newSkill = new Skill();
            JsonElement skill = activeSkills.get(i).getAsJsonObject().get("skill");
            JsonElement rune = activeSkills.get(i).getAsJsonObject().get("rune");

            newSkill.setName(skill.getAsJsonObject().get("name").getAsString());
            newSkill.setIcon(skill.getAsJsonObject().get("icon").getAsString());
            newSkill.setRune(rune.getAsJsonObject().get("name").getAsString());

            skillList.add(newSkill);
        }

        for (int i = 0; i < passiveSkills.size(); i++) {
            Skill newSkill = new Skill();

            JsonElement skill = passiveSkills.get(i).getAsJsonObject().get("skill");

            newSkill.setName(skill.getAsJsonObject().get("name").getAsString());
            newSkill.setIcon(skill.getAsJsonObject().get("icon").getAsString());
            newSkill.setRune("");

            skillList.add(newSkill);
        }


        return skillList;
    }

    public ItemStats parseItemStats(String itemString)  {
        final ItemStats itemStats = new ItemStats();
        mService.getItem(itemString, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement element, Response response) {
                JsonObject data = element.getAsJsonObject();
                Log.v("REST", element.toString());
                itemStats.setFlavorText(data.get("flavorText").getAsString());
                itemStats.setIcon(data.get("icon").getAsString());
                itemStats.setItemLevel(data.get("itemLevel").getAsInt());
                itemStats.setRequiredLevel(data.get("requiredLevel").getAsInt());
                getRawAttributes(element);
                getRolledAttributes(element);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v("REST", retrofitError.toString());
            }
        });
        return itemStats;
    }

    public List<RawAttributes> getRawAttributes(JsonElement element) {
        JsonObject object = element.getAsJsonObject().get("attributesRaw").getAsJsonObject();
        List<RawAttributes> attribs = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            RawAttributes singleAttrib = new RawAttributes();
            singleAttrib.setAttrib(entry.getKey());
            singleAttrib.setMin(entry.getValue().getAsJsonObject().get("min").getAsFloat());
            singleAttrib.setMax(entry.getValue().getAsJsonObject().get("max").getAsFloat());
            attribs.add(singleAttrib);
        }
        return attribs;
    }

    public List<RolledAttributes> getRolledAttributes(JsonElement element) {
        JsonObject object = element.getAsJsonObject().get("attributes").getAsJsonObject();
        JsonArray primaryArray = object.getAsJsonObject().get("primary").getAsJsonArray();
        JsonArray secondaryArray = object.getAsJsonObject().get("secondary").getAsJsonArray();

        List<RolledAttributes> rolledAttributes = new ArrayList<>();

        for (JsonElement attrib : primaryArray) {
            RolledAttributes singleAttrib = new RolledAttributes();
            singleAttrib.setPrimary(true);
            singleAttrib.setText(attrib.getAsJsonObject().get("text").getAsString());
            singleAttrib.setAffixType(attrib.getAsJsonObject().get("affixType").getAsString());
            singleAttrib.setColor(attrib.getAsJsonObject().get("color").getAsString());

            rolledAttributes.add(singleAttrib);
        }

        for (JsonElement attrib : secondaryArray) {
            RolledAttributes singleAttrib = new RolledAttributes();
            singleAttrib.setPrimary(false);
            singleAttrib.setText(attrib.getAsJsonObject().get("text").getAsString());
            singleAttrib.setAffixType(attrib.getAsJsonObject().get("affixType").getAsString());
            singleAttrib.setColor(attrib.getAsJsonObject().get("color").getAsString());

            rolledAttributes.add(singleAttrib);
        }

        return rolledAttributes;
    }
}

class statsDeserializer implements JsonDeserializer<HeroStats> {

    @Override
    public HeroStats deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement content = jsonElement.getAsJsonObject().get("stats");
        return new Gson().fromJson(content, HeroStats.class);
    }
}


//class heroesDeserializer implements JsonDeserializer<List<Hero>> {
//    List<Hero> heroes = new ArrayList<>();
//    @Override
//    public List<Hero> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonArray list = jsonElement.getAsJsonArray();
//        for (JsonElement heroJson : list) {
//            Hero h = jsonDeserializationContext.deserialize(heroJson, Hero.class);
//            heroes.add(h);
//        }
//
//        return heroes;
//    }
//}
