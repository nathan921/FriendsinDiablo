package com.ddtpt.friendsindiablo;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e228596 on 2/19/2015.
 */
public class FragmentCareerList extends Fragment {
    private ArrayList<String> mStoredCareers;
    private ArrayList<Hero> mHeroes;
    private static final String ENDPOINT = "http://us.battle.net";
    private static final String FILENAME = "careers.json";
    private Utils mUtils;
    private RESTUtils mRESTUtils;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;
    private Career mCareer;
    private Hero mHero;
    private int mCurrentPosition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: GET STORED CHARACTER LIST AND FILL THE ARRAYLIST
        mUtils = new Utils(getActivity(), FILENAME);
        mRESTUtils = new RESTUtils();

        mHeroes = new ArrayList<>();

        mStoredCareers = new ArrayList<>();
        mStoredCareers = mUtils.getCareerList();
        mCareer = new Career();
        mHero = new Hero();
        mCurrentPosition = 0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.career_list_recycler_layout, parent, false);

        recList = (RecyclerView)v.findViewById(R.id.careerList);
        ActionButton actionButton = (ActionButton)v.findViewById(R.id.add_button);

        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("CareerList", "Button was clicked");
                new MaterialDialog.Builder(getActivity())
                        .title("Enter Battle.net ID")
                        .customView(R.layout.input_text_view_for_add_career_dialog, true)
                        .positiveText("Add")
                        .negativeText("Cancel")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                EditText user = (EditText) dialog.findViewById(R.id.career_addition);
                                String username = user.getText().toString();
                                //TODO: Update with regex to determine is format is proper.
                                if (!username.equals("")) {
                                    updateCareerList(user.getText().toString());
                                }
                            }
                        })
                        .show();
            }
        });

        new Thread(new Runnable() {
            @Override
        public void run() {
                mAdapter = new CareerAdapter(mStoredCareers);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recList.setAdapter(mAdapter);
                    }
                });
            }
        }).start();

        return v;
    }

    private void getCharacters(int index) {
        String name = mStoredCareers.get(index);
        mCareer = mRESTUtils.getCareerDetails(name);
    }

    public void loadCharactersIntoList() {
        mHeroes = mCareer.getHeroes();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new HeroAdapter(mHeroes);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recList.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public class CareerAdapter extends RecyclerView.Adapter<CareerViewHolder> {
        private ArrayList<String> careerList;

        public CareerAdapter(ArrayList<String> chars) {
            careerList = chars;
        }

        @Override
        public int getItemCount() {
            return careerList.size();
        }

        @Override
        public void onBindViewHolder(CareerViewHolder cvh, int pos) {
            String careerName = careerList.get(pos);
            final int position = pos;
            cvh.vName.setText(careerName);

            //TODO: Swipeable View to remove names from the list
            cvh.vName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCharacters(position);
                    Log.v("Name Click Listener", "Flip the fucking dick down the pussy: " + Integer.toString(position));
                }
            });

        }

        @Override
        public CareerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.career_card_layout, viewGroup, false);
            return new CareerViewHolder(itemView);
        }
    }

    public class HeroAdapter extends RecyclerView.Adapter<CharacterViewHolder> {
        private ArrayList<Hero> heroList;
        private int currentPosition;

        public HeroAdapter(ArrayList<Hero> heroes) {
            heroList = heroes;
        }

        @Override
        public int getItemCount() {
            return heroList.size();
        }

        @Override
        public void onBindViewHolder(CharacterViewHolder cvh, int pos) {
            String heroName = heroList.get(pos).getName();
            final int position = pos;



            cvh.vName.setText(heroName);
            cvh.vParagon.setText(heroList.get(pos).getParagonLevel());
            cvh.vClass.setText(heroList.get(pos).getHero_class());

            String heroClass = heroList.get(pos).getHero_class();
            int heroSex = heroList.get(pos).getGender();

            if (heroList.get(pos).isHardcore()) {
                //cvh.vHardcore.setImageDrawable(getResources().getDrawable(R.drawable.hardcore));
            }

            if (heroList.get(pos).isSeasonal()) {
                //cvh.vSeasonal.setImageDrawable(getResources().getDrawable(R.drawable.seasonal));
            }

            if (heroSex == 0) {
                switch (heroClass) {
                    case "barbarian":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.barbarian_male));
                        break;

                    case "demon-hunter":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.demonhunter_male));
                        break;

                    case "wizard":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.wizard_male));
                        break;

                    case "monk":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.monk_male));
                        break;

                    case "witch-doctor":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.witchdoctor_male));
                        break;
                }
            } else {
                switch (heroClass) {
                    case "barbarian":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.barbarian_female));
                        break;

                    case "demon-hunter":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.demonhunter_female));
                        break;

                    case "wizard":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.wizard_female));
                        break;

                    case "monk":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.monk_female));
                        break;

                    case "witch-doctor":
                        cvh.vPortrait.setImageDrawable(getResources().getDrawable(R.drawable.witchdoctor_female));
                        break;
                }

            }
        }

        @Override
        public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hero_card_layout, viewGroup, false);
            return new CharacterViewHolder(itemView);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView vName, vClass, vParagon;
        protected ImageView vHardcore, vSeasonal, vPortrait;

        public CharacterViewHolder(View v) {
            super(v);
            vPortrait = (ImageView)v.findViewById(R.id.character_icon);
            vName = (TextView)v.findViewById(R.id.character_name);
            vClass = (TextView)v.findViewById(R.id.char_class);
            vParagon = (TextView)v.findViewById(R.id.paragon_level);
            vHardcore = (ImageView)v.findViewById(R.id.hardcore_image);
            vSeasonal = (ImageView)v.findViewById(R.id.seasonal_image);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Position: " + getPosition(), Toast.LENGTH_SHORT).show();
            testItemDownload(getPosition());
        }
    }
    //TODO: Update this ViewHolder with OnClick method isn't of applying it above.
    public static class CareerViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;

        public CareerViewHolder(View v) {
            super(v);
            vName = (TextView)v.findViewById(R.id.career_name);
        }
    }

    public void testItemDownload(int position) {
        mCurrentPosition = position;
        mHero = mRESTUtils.getHeroDetails(mCareer.getBattleTag(), mCareer.getHeroes().get(position).id);
    }

    public void populateHeroData() {
        mCareer.getHeroes().get(mCurrentPosition).setHeroStats(mHero.getHeroStats());
        mCareer.getHeroes().get(mCurrentPosition).setSkills(mHero.getSkills());
        mCareer.getHeroes().get(mCurrentPosition).setItems(mHero.getItems());
    }

    public void updateCareerList(String name) {
        mStoredCareers.add(name);
        mUtils.storeCareerList(mStoredCareers);
        mAdapter.notifyDataSetChanged();
    }


}

