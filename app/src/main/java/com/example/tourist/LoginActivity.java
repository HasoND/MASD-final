package com.example.tourist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login, register;
    private UserDataHelper userDataHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);
        userDataHelper = new UserDataHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernames = username.getText().toString();
                String passwords = password.getText().toString();
                boolean checkLogin = userDataHelper.checkUserpassword(usernames, passwords);
                if(checkLogin){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("username",usernames);
                        jsonObject.put("success",true);

                        SessionManager.createSession(LoginActivity.this, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    username.setText("");
                    password.setText("");
                    Toast.makeText(LoginActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
           }
       });


    }
}
