package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class ModifyProject extends AppCompatActivity {
    Button saveBtn;
    Button deleteBtn;
    Button cancelBtn;
    ImageButton carBtn;
    ImageButton houseBtn;
    ImageButton graduateBtn;
    String type;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ModifyProject.this, MainActivity.class);
        ModifyProject.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_project);
        final EditText projectName = findViewById(R.id.nameEdit);
        final EditText amount = findViewById(R.id.amountEdit);
        carBtn = findViewById(R.id.carBtn);
        houseBtn = findViewById(R.id.houseBtn);
        graduateBtn = findViewById(R.id.graduateBtn);
        final String projectToModify = getIntent().getStringExtra("PROJECT_ID");
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.projects),MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String projectArray = sharedPreferences.getString(getResources().getString(R.string.projects),"");
        Gson gson = new Gson();
        Type cls = new TypeToken< ArrayList < Project >>() {}.getType();
        final ArrayList<Project> projects = gson.fromJson(projectArray, cls);
        final Project toModify = projects.get(Integer.parseInt(projectToModify)-1);
        projectName.setText(toModify.getName());
        amount.setText(String.valueOf(toModify.getAmount()));
        System.out.println(toModify.getType());
        if(toModify.getType().equals("car")){
            carBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
            houseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            graduateBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else if(toModify.getType().equals("house")){
            houseBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
            carBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            graduateBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        }else{
            graduateBtn.setBackground(getResources().getDrawable(R.drawable.btnborder));
            carBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            houseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = projectName.getText().toString();
                int newAmount = Integer.parseInt(amount.getText().toString());
                toModify.setAmount(newAmount);
                toModify.setName(newName);
                toModify.setType(type);
                projects.set(Integer.parseInt(projectToModify)-1,toModify);
                Gson gson = new Gson();
                editor.putString(getResources().getString(R.string.projects),gson.toJson(projects));
                editor.apply();
                Intent intent = new Intent(view.getContext(), UserActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        deleteBtn = findViewById(R.id.deleteBTn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Project")
                        .setMessage("Do you really want to delete this project?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Gson gson = new Gson();
                                projects.remove(Integer.parseInt(projectToModify)-1);
                                editor.putString(getResources().getString(R.string.projects),gson.toJson(projects));
                                editor.apply();
                                Intent intent = new Intent(ModifyProject.this, UserActivity.class);
                                ModifyProject.this.startActivity(intent);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
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