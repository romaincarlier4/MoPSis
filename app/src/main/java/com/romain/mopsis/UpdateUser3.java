package com.romain.mopsis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUser3 extends AppCompatActivity {

    EditText name;
    Button confirm;
    public static final String SHARED_PREFS = "user3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user3);

        name = findViewById(R.id.username);
        confirm = findViewById(R.id.confirmButton);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String oldUsername = sharedPreferences.getString("username",null);
        name.setText(oldUsername);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",name.getText().toString());
                editor.apply();
                Intent userFragment = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(userFragment);
            }
        });
    }
}