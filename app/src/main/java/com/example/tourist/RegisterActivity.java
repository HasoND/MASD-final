package com.example.tourist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password, phone;
    private RadioGroup gender;
    private Button create;
    private UserDataHelper userDataHelper;
    private boolean isMale;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.et_register_username);
        email = findViewById(R.id.et_register_email);
        phone = findViewById(R.id.et_register_phone);
        password = findViewById(R.id.et_register_password);
        gender = findViewById(R.id.rd_gender);
        create = findViewById(R.id.bt_register_create);
        userDataHelper = new UserDataHelper(this);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isMale = i == R.id.rd_bt_male;
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernames = username.getText().toString();
                String emails = email.getText().toString();
                String passwords = password.getText().toString();
                String phones = phone.getText().toString();
                String gender = "";
                if(usernames.equals("")||emails.equals("")||passwords.equals("")||phones.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkuser = userDataHelper.checkUsername(usernames);
                    if(!checkuser){
                        if(isMale){
                            gender = "male";
                        }else{
                            gender = "female";
                        }
                        boolean isInserted = userDataHelper.insertUser(usernames,emails, phones, gender,passwords, 0);
                        if(!isInserted){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Fail to register. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



    }
}
