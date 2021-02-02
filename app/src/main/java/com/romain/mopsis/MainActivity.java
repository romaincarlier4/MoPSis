package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ImageButton userBtn;
    Spinner choice;
    Fragment toDisplay;
    Boolean choiceInit = false;
    ImageButton project1;
    ImageButton project2;
    ImageButton project3;
    ImageButton project4;
    public int id = -1;
    String choiceCalc;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        if(name.equals("")){
            setContentView(R.layout.welcome_first_time);
            Button go = findViewById(R.id.letsgo);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText nameFirstTime = findViewById(R.id.nameFirstTime);
                    editor.putString("name",nameFirstTime.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            setContentView(R.layout.activity_main);
            choice = findViewById(R.id.choiceCalculate);
            choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!choiceInit) {
                        choiceInit = true;
                        return;
                    }
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("toCalculate", choice.getSelectedItem().toString());
                    view.getContext().startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });

            choiceCalc = getIntent().getStringExtra("toCalculate");
            if (choiceCalc == null){
                choiceCalc = getResources().getString(R.string.months);
            }
            if(choiceCalc.equals(getResources().getString(R.string.months))){
                toDisplay = new PayFragmentMonths();
                choice.setSelection(0);
            } else if(choiceCalc.equals(getResources().getString(R.string.rate))){
                toDisplay = new PayFragmentRate();
                choice.setSelection(1);
            } else if(choiceCalc.equals(getResources().getString(R.string.duration))) {
                toDisplay = new PayFragmentDuration();
                choice.setSelection(2);
            } else if(choiceCalc.equals(getResources().getString(R.string.amount))) {
                toDisplay = new PayFragmentAmount();
                choice.setSelection(3);
            }
            TextView welcome = findViewById(R.id.welcomeUser);
            welcome.setText("Hello, " + name + ".");

            //Gestion des fragments et de la tabLayout

            TabLayout tabLayout = findViewById(R.id.toolbar);
            final ViewPager viewPager = findViewById(R.id.viewPager);
            viewPager.setOffscreenPageLimit(2);

            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

            viewPagerAdapter.addFragment(toDisplay);
            viewPagerAdapter.addFragment(new GraphicsFragment());

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            int[] icons = {R.drawable.pay, R.drawable.graph};
            String[] texts = {"Simulate","Data"};

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                tabLayout.getTabAt(i).setIcon(icons[i]);
                tabLayout.getTabAt(i).setText(texts[i]);
            }

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            userBtn = findViewById(R.id.userBtn);
            userBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), UserActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

            project1 = findViewById(R.id.project1Home);
            project2 = findViewById(R.id.project2Home);
            project3 = findViewById(R.id.project3Home);
            project4 = findViewById(R.id.project4Home);
            project1.setBackgroundColor(getResources().getColor(R.color.transparent));
            project2.setBackgroundColor(getResources().getColor(R.color.transparent));
            project3.setBackgroundColor(getResources().getColor(R.color.transparent));
            project4.setBackgroundColor(getResources().getColor(R.color.transparent));

            project1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    id = 0;
                    getSupportFragmentManager().beginTransaction().detach(toDisplay).attach(toDisplay).commit();
                }
            });
            project2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    id = 1;
                    getSupportFragmentManager().beginTransaction().detach(toDisplay).attach(toDisplay).commit();
                }
            });
            project3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    id = 2;
                    getSupportFragmentManager().beginTransaction().detach(toDisplay).attach(toDisplay).commit();
                }
            });
            project4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    project1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    id = 3;
                    getSupportFragmentManager().beginTransaction().detach(toDisplay).attach(toDisplay).commit();
                }
            });


            SharedPreferences sharedPreferencesProjects = getSharedPreferences(getResources().getString(R.string.projects),MODE_PRIVATE);
            String projectsArray = sharedPreferencesProjects.getString(getResources().getString(R.string.projects),"");
            Gson gson = new Gson();
            Type cls = new TypeToken<ArrayList<Project>>(){}.getType();
            ArrayList<Project> projects = gson.fromJson(projectsArray, cls);

            if(projects==null){
                projects = new ArrayList<>();
            }
            String[] images = new String[projects.size()];
            for (int i = 0; i < projects.size(); i++) {
                images[i] = projects.get(i).getImageTypeString();
            }
            switch (projects.size()){
                case 0 :
                    project1.setVisibility(View.INVISIBLE);
                    project2.setVisibility(View.INVISIBLE);
                    project3.setVisibility(View.INVISIBLE);
                    project4.setVisibility(View.INVISIBLE);
                    break;
                case 1 :
                    id = 0;
                    project1.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project1.setVisibility(View.VISIBLE);
                    setSrc(project1, images[0]);
                    project2.setVisibility(View.INVISIBLE);
                    project3.setVisibility(View.INVISIBLE);
                    project4.setVisibility(View.INVISIBLE);
                    break;
                case 2 :
                    id = 0;
                    project1.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project1.setVisibility(View.VISIBLE);
                    setSrc(project1, images[0]);
                    project2.setVisibility(View.VISIBLE);
                    setSrc(project2, images[1]);
                    project3.setVisibility(View.INVISIBLE);
                    project4.setVisibility(View.INVISIBLE);
                    break;
                case 3 :
                    id = 0;
                    project1.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project1.setVisibility(View.VISIBLE);
                    setSrc(project1, images[0]);
                    project2.setVisibility(View.VISIBLE);
                    setSrc(project2, images[1]);
                    project3.setVisibility(View.VISIBLE);
                    setSrc(project3, images[2]);
                    project4.setVisibility(View.INVISIBLE);
                    break;
                case 4 :
                    id = 0;
                    project1.setBackground(getResources().getDrawable(R.drawable.btnborder));
                    project1.setVisibility(View.VISIBLE);
                    setSrc(project1, images[0]);
                    project2.setVisibility(View.VISIBLE);
                    setSrc(project2, images[1]);
                    project3.setVisibility(View.VISIBLE);
                    setSrc(project3, images[2]);
                    project4.setVisibility(View.VISIBLE);
                    setSrc(project4, images[3]);
                    break;
            }
        }
    }

    public void setSrc(ImageButton btn, String type){
        if(type.equals("car")){
            btn.setImageResource(R.drawable.babycar);
        } else if(type.equals("house")){
            btn.setImageResource(R.drawable.house);
        } else{
            btn.setImageResource(R.drawable.graduated);
        }
    }

}