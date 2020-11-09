package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser3Activity extends AppCompatActivity {

    EditText username3;
    Button confirm3;
    public static final String SHARED_PREFS = "Usernames";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user3);

        username3 = findViewById(R.id.username3);
        confirm3 = findViewById(R.id.confirmButton3);

        confirm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username3",username3.getText().toString());
                editor.apply();
                Intent userFragment = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(userFragment);
            }
        });
    }
}