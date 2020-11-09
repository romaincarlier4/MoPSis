package com.romain.mopsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUserActivity extends AppCompatActivity {

    EditText name;
    Button confirm;
    public static final String SHARED_PREFS = "Usernames";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        name = findViewById(R.id.userName);
        confirm = findViewById(R.id.confirmButton);

       confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username1",name.getText().toString());
                editor.apply();
                Intent userFragment = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(userFragment);
            }
        });
    }
}