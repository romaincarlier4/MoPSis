package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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


public class MainActivity extends AppCompatActivity {

    ImageButton userBtn;
    Spinner choice;
    Fragment toDisplay;
    Boolean choiceInit = false;


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
            String choiceCalc = getIntent().getStringExtra("toCalculate");
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
            welcome.setText("Hello, "+name+".");
            //Gestion des fragments et de la tabLayout
            TabLayout tabLayout = findViewById(R.id.toolbar);
            final ViewPager viewPager = findViewById(R.id.viewPager);
            viewPager.setOffscreenPageLimit(2);

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

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
        }


    }

}