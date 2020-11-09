package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser2Activity extends AppCompatActivity {

    EditText username2;
    Button confirm2;
    public static final String SHARED_PREFS = "user2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);

        username2 = findViewById(R.id.username2);
        confirm2 = findViewById(R.id.confirmButton2);

        confirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",username2.getText().toString());
                editor.apply();
                Intent userFragment = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(userFragment);
            }
        });
    }
}