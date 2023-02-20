package com.example.tourist;

import static com.example.tourist.SessionManager.USERNAME;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private Button logoutButton;
    private ImageButton edit_profile;
    private TextView usernames, emails, phones, genders, points;
    private LinearLayout linearLayout;

    private UserDataHelper userDataHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        logoutButton = view.findViewById(R.id.bt_logout);
        edit_profile = view.findViewById(R.id.ib_edit_profile);

        usernames = view.findViewById(R.id.tv_profile_username);
        emails = view.findViewById(R.id.tv_profile_email);
        phones = view.findViewById(R.id.tv_profile_phone);
        genders = view.findViewById(R.id.tv_profile_gender);
        points = view.findViewById(R.id.tv_profile_point);
        linearLayout = view.findViewById(R.id.linear_profile);

        userDataHelper = new UserDataHelper(getActivity().getApplicationContext());

        try {
            JSONObject userdetails = userDataHelper.getUserdetails(SessionManager.getUserDetail(getContext()).get(USERNAME));
            usernames.setText(userdetails.getString("username"));
            emails.setText(userdetails.getString("email"));
            genders.setText(userdetails.getString("gender"));
            phones.setText(userdetails.getString("phone"));Log.d("userdetails: ",userdetails.getInt("point")+" ");
            points.setText(String.valueOf(userDataHelper.getVoucherCount()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),VoucherActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.logout(getActivity());
            }
        });
    }
}