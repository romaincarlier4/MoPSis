package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    Button backBtn;
    ArrayList<Project> projects;
    Button firstUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.projects), MODE_PRIVATE);
        String projectArray = sharedPreferences.getString(getResources().getString(R.string.projects),"");
        Gson gson = new Gson();
        Type cls = new TypeToken< ArrayList < Project >>() {}.getType();
        projects = gson.fromJson(projectArray,cls);
        if(projects==null){
            projects = new ArrayList<>();
        }
        switch(projects.size()){
            case 0 :
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_empty);
                firstUser = findViewById(R.id.createBtn);
                firstUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                break;

            case 1:
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_1projects);
                ImageButton oneUserProject1 = findViewById(R.id.project1Image);
                TextView oneUserText1 = findViewById(R.id.txtproject1);
                ImageButton oneUserAdd2 = findViewById(R.id.add2);
                ImageButton oneUserAdd3 = findViewById(R.id.add3);
                ImageButton oneUserAdd4 = findViewById(R.id.add4);
                oneUserProject1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "1");
                        view.getContext().startActivity(intent);
                    }
                });
                oneUserAdd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                oneUserAdd3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                oneUserAdd4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                String proName = projects.get(0).getName();
                String proType = projects.get(0).getType();
                System.out.println(proType);
                if(proType.equals("car")){
                    oneUserProject1.setBackgroundResource(R.drawable.bigcar);
                } else if (proType.equals("house")){
                    oneUserProject1.setBackgroundResource(R.drawable.bighouse);
                } else {
                    oneUserProject1.setBackgroundResource(R.drawable.biggraduate);
                }
                oneUserText1.setText(proName);
                break;

            case 2 :
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_2projects);
                ImageButton twoUserProject1 = findViewById(R.id.project1Image);
                TextView twoUserText1 = findViewById(R.id.txtproject1);
                ImageButton twoUserProject2 = findViewById(R.id.project2Image);
                TextView twoUserText2 = findViewById(R.id.txtproject2);
                ImageButton twoUserAdd3 = findViewById(R.id.add3);
                ImageButton twoUserAdd4 = findViewById(R.id.add4);
                twoUserProject1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "1");
                        view.getContext().startActivity(intent);
                    }
                });
                twoUserProject2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "2");
                        view.getContext().startActivity(intent);
                    }
                });
                twoUserAdd3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                twoUserAdd4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                String proName1 = projects.get(0).getName();
                String proType1 = projects.get(0).getType();
                String proName2 = projects.get(1).getName();
                String proType2 = projects.get(1).getType();
                if(proType1.equals("car")){
                    twoUserProject1.setBackgroundResource(R.drawable.bigcar);
                } else if (proType1.equals("house")){
                    twoUserProject1.setBackgroundResource(R.drawable.bighouse);
                } else {
                    twoUserProject1.setBackgroundResource(R.drawable.biggraduate);
                }
                if(proType2.equals("car")){
                    twoUserProject2.setBackgroundResource(R.drawable.bigcar);
                } else if (proType2.equals("house")){
                    twoUserProject2.setBackgroundResource(R.drawable.bighouse);
                } else {
                    twoUserProject2.setBackgroundResource(R.drawable.biggraduate);
                }
                twoUserText1.setText(proName1);
                twoUserText2.setText(proName2);
                break;

            case 3:
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_3projects);
                ImageButton threeUserProject1 = findViewById(R.id.project1Image);
                TextView threeUserText1 = findViewById(R.id.txtproject1);
                ImageButton threeUserProject2 = findViewById(R.id.project2Image);
                TextView threeUserText2 = findViewById(R.id.txtproject2);
                ImageButton threeUserProject3 = findViewById(R.id.project3Image);
                TextView threeUserText3 = findViewById(R.id.txtproject3);
                ImageButton threeUserAdd4 = findViewById(R.id.add4);
                threeUserProject1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "1");
                        view.getContext().startActivity(intent);
                    }
                });
                threeUserProject2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "2");
                        view.getContext().startActivity(intent);
                    }
                });
                threeUserProject3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "3");
                        view.getContext().startActivity(intent);
                    }
                });
                threeUserAdd4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), UserCreation.class);
                        view.getContext().startActivity(intent);
                    }
                });
                String threeproName1 = projects.get(0).getName();
                String threeproType1 = projects.get(0).getType();
                String threeproName2 = projects.get(1).getName();
                String threeproType2 = projects.get(1).getType();
                String threeproName3 = projects.get(2).getName();
                String threeproType3 = projects.get(2).getType();
                if(threeproType1.equals("car")){
                    threeUserProject1.setBackgroundResource(R.drawable.bigcar);
                } else if (threeproType1.equals("house")){
                    threeUserProject1.setBackgroundResource(R.drawable.bighouse);
                } else {
                    threeUserProject1.setBackgroundResource(R.drawable.biggraduate);
                }
                if(threeproType2.equals("car")){
                    threeUserProject2.setBackgroundResource(R.drawable.bigcar);
                } else if (threeproType2.equals("house")){
                    threeUserProject2.setBackgroundResource(R.drawable.bighouse);
                } else {
                    threeUserProject2.setBackgroundResource(R.drawable.biggraduate);
                }
                if(threeproType3.equals("car")){
                    threeUserProject3.setBackgroundResource(R.drawable.bigcar);
                } else if (threeproType3.equals("house")){
                    threeUserProject3.setBackgroundResource(R.drawable.bighouse);
                } else {
                    threeUserProject3.setBackgroundResource(R.drawable.biggraduate);
                }
                threeUserText1.setText(threeproName1);
                threeUserText2.setText(threeproName2);
                threeUserText3.setText(threeproName3);
                break;

            case 4 :
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_4projects);
                ImageButton fourUserProject1 = findViewById(R.id.project1Image);
                TextView fourUserText1 = findViewById(R.id.txtproject1);
                ImageButton fourUserProject2 = findViewById(R.id.project2Image);
                TextView fourUserText2 = findViewById(R.id.txtproject2);
                ImageButton fourUserProject3 = findViewById(R.id.project3Image);
                TextView fourUserText3 = findViewById(R.id.txtproject3);
                ImageButton fourUserProject4 = findViewById(R.id.project4Image);
                TextView fourUserText4 = findViewById(R.id.txtproject4);
                fourUserProject1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "1");
                        view.getContext().startActivity(intent);
                    }
                });
                fourUserProject2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "2");
                        view.getContext().startActivity(intent);
                    }
                });
                fourUserProject3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "3");
                        view.getContext().startActivity(intent);
                    }
                });
                fourUserProject4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ModifyProject.class);
                        intent.putExtra("PROJECT_ID", "4");
                        view.getContext().startActivity(intent);
                    }
                });
                String fourproName1 = projects.get(0).getName();
                String fourproType1 = projects.get(0).getType();
                String fourproName2 = projects.get(1).getName();
                String fourproType2 = projects.get(1).getType();
                String fourproName3 = projects.get(2).getName();
                String fourproType3 = projects.get(2).getType();
                String fourproName4 = projects.get(3).getName();
                String fourproType4 = projects.get(3).getType();
                if(fourproType1.equals("car")){
                    fourUserProject1.setBackgroundResource(R.drawable.bigcar);
                } else if (fourproType1.equals("house")){
                    fourUserProject1.setBackgroundResource(R.drawable.bighouse);
                } else {
                    fourUserProject1.setBackgroundResource(R.drawable.biggraduate);
                }
                if(fourproType2.equals("car")){
                    fourUserProject2.setBackgroundResource(R.drawable.bigcar);
                } else if (fourproType2.equals("house")){
                    fourUserProject2.setBackgroundResource(R.drawable.bighouse);
                } else {
                    fourUserProject2.setBackgroundResource(R.drawable.biggraduate);
                }
                if(fourproType3.equals("car")){
                    fourUserProject3.setBackgroundResource(R.drawable.bigcar);
                } else if (fourproType3.equals("house")){
                    fourUserProject3.setBackgroundResource(R.drawable.bighouse);
                } else {
                    fourUserProject3.setBackgroundResource(R.drawable.biggraduate);
                }
                if(fourproType4.equals("car")){
                    fourUserProject4.setBackgroundResource(R.drawable.bigcar);
                } else if (fourproType4.equals("house")){
                    fourUserProject4.setBackgroundResource(R.drawable.bighouse);
                } else {
                    fourUserProject4.setBackgroundResource(R.drawable.biggraduate);
                }
                fourUserText1.setText(fourproName1);
                fourUserText2.setText(fourproName2);
                fourUserText3.setText(fourproName3);
                fourUserText4.setText(fourproName4);
                break;
        }

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }
}