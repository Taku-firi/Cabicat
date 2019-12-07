package com.Stardust.cabicat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangepwdActivity extends AppCompatActivity {

    Button btnApply;
    EditText username,cpassword,npassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);

        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        cpassword = findViewById(R.id.cpassword);
        npassword = findViewById(R.id.npassword);

        btnApply = findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {

            SharedPreferences pref = getSharedPreferences("cabidata",MODE_PRIVATE);
            boolean registered = pref.getBoolean("registered",false);
            String Username = pref.getString("username","");
            String Password = pref.getString("pwd","");

            @Override
            public void onClick(View view) {
                String username_input = username.getText().toString();
                String cpassword_input = cpassword.getText().toString();
                String npassword_input = npassword.getText().toString();

                if (registered){
                    if (username_input.equals(Username) && md5(cpassword_input).equals(Password)){
                        if (npassword_input.length() == 6) {
                            SharedPreferences.Editor editor = getSharedPreferences("cabidata", MODE_PRIVATE).edit();
                            editor.putString("pwd", md5(npassword_input));
                            editor.apply();
                            Toast.makeText(getApplication(), "Changed successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplication(), "Password should be a six figure.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplication(), "Unauthorized username-password pair", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplication(), "Please register first ~", Toast.LENGTH_SHORT).show();
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
