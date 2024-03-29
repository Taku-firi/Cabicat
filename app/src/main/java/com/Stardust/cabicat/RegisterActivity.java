package com.Stardust.cabicat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            SharedPreferences pref = getSharedPreferences("cabidata",MODE_PRIVATE);
            boolean registered = pref.getBoolean("registered",false);

            @Override
            public void onClick(View view) {
                String username_input = username.getText().toString();
                String password_input = password.getText().toString();
                if(!TextUtils.isEmpty(username_input)){
                    if (password_input.length()==6){
                        if(!registered ) {
                            SharedPreferences.Editor editor = getSharedPreferences("cabidata", MODE_PRIVATE).edit();
                            editor.putString("username", username_input);
                            editor.putString("pwd", md5(password_input));
                            editor.putBoolean("registered", true);
                            editor.apply();
                            Toast.makeText(getApplication(), "Registered successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplication(), "Cabicat already has a user.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplication(), "Password should be a six figure.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(), "Username should not be empty.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
