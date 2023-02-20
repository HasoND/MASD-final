package com.example.tourist;

import static com.example.tourist.SessionManager.USERNAME;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    public static boolean needReload = false;

    private EditText uname, email, phone, password;
    private Button update;
    private RadioGroup gender;
    private RadioButton male, female;
    private UserDataHelper userDataHelper;
    private boolean isMale;

    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        uname = findViewById(R.id.et_edit_username);
        email = findViewById(R.id.et_edit_email);
        phone = findViewById(R.id.et_edit_phone);
        password = findViewById(R.id.et_edit_password);
        gender = findViewById(R.id.rd_gender);
        male = findViewById(R.id.rd_edit_bt_male);
        female = findViewById(R.id.rd_edit_bt_female);
        update = findViewById(R.id.bt_edit_update);
        userDataHelper = new UserDataHelper(this);

        try {
            JSONObject userdetails = userDataHelper.getUserdetails(SessionManager.getUserDetail(this).get(USERNAME));
            Log.d("user", SessionManager.getUserDetail(this).get(USERNAME));
            id = userdetails.getInt("id");
            String usernames = userdetails.getString("username");
            String emails = userdetails.getString("email");
            String phones = userdetails.getString("phone");
            String genders = userdetails.getString("gender");
            String passwords = userdetails.getString("password");

            uname.setText(usernames);
            email.setText(emails);
            phone.setText(phones);
            if(genders.equals("male")){
                male.setChecked(true);
                isMale = true;
            }else{
                female.setChecked(true);
                isMale = false;
            }
            password.setText(passwords);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("test", (isMale = i == R.id.rd_edit_bt_male)+" ");
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Log.d("check update: ", uname.getText());*/
                if(!uname.getText().toString().equals("")||!email.getText().toString().equals("")|| !phone.getText().toString().equals("")|| !password.getText().toString().equals("")){

                    String usernames = uname.getText().toString();
                    String emails = email.getText().toString();
                    String phones = phone.getText().toString();
                    String genders = "";
                    Log.d("ismale?", isMale+" ");
                    if(isMale){
                        genders = "male";
                    }else{
                        genders = "female";
                    }
                    String passwords = password.getText().toString();

                    boolean isUpdated = userDataHelper.updateUserdetails(id, usernames, emails, phones, genders, passwords);
                    if(isUpdated){
                        Toast.makeText(EditProfileActivity.this, "Updated your profile.", Toast.LENGTH_SHORT).show();
                        needReload = true;
                        finish();
                    }else{
                        Toast.makeText(EditProfileActivity.this, "Fail to update your profile.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditProfileActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
