package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class UserCreation extends AppCompatActivity {

    Button cancelBtn;
    Button saveBtn;
    EditText projectName;
    EditText amount;
    ImageButton carBtn;
    ImageButton houseBtn;
    ImageButton graduateBtn;
    String type;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn = findViewById(R.id.saveBtn);
        projectName = findViewById(R.id.nameEdit);
        amount = findViewById(R.id.amountEdit);
        carBtn = findViewById(R.id.carBtn);
        houseBtn = findViewById(R.id.houseBtn);
        graduateBtn = findViewById(R.id.graduateBtn);
        errorMessage = findViewById(R.id.errorMessage);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.projects), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String projectArray = sharedPreferences.getString(getResources().getString(R.string.projects),"");
                if(projectName.getText().toString().equals("") || amount.getText().toString().equals("") || type==null){
                    errorMessage.setText("You must complete all fields");
                    errorMessage.setVisibility(View.VISIBLE);
                }
                else {
                    Project newProject = new Project(projectName.getText().toString(), Integer.parseInt(amount.getText().toString()), type);
                    Gson gson = new Gson();
                    Type cls = new TypeToken<ArrayList<Project>>() {
                    }.getType();
                    ArrayList<Project> projects = gson.fromJson(projectArray, cls);
                    if (projects == null) {
                        projects = new ArrayList<>();
                    }
                    projects.add(newProject);
                    editor.putString(getResources().getString(R.string.projects), gson.toJson(projects));
                    editor.apply();
                    errorMessage.setText("");
                    errorMessage.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(view.getContext(), UserActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });

        carBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
                houseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                graduateBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                type = "car";
            }
        });

        houseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
                carBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                graduateBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                type = "house";
            }
        });

        graduateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graduateBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
                houseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                carBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                type = "graduate";
            }
        });
    }
}