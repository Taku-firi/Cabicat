package com.Stardust.cabicat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    Button button;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_input = username.getText().toString();
                String password_input = password.getText().toString();
                if(TextUtils.isEmpty(username_input)){
                    username_input="0";
                }
                if(TextUtils.isEmpty(password_input)){
                    password_input="0";
                }
                SharedPreferences.Editor editor = getSharedPreferences("cabidata",MODE_PRIVATE).edit();
                editor.putBoolean("checked",false);
                editor.putString("username", username_input);
                editor.putString("password",password_input);
                editor.apply();
            }
        });

    }
}
