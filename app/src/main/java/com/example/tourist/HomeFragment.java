package com.example.tourist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class HomeFragment extends Fragment implements SuggestAdapter.onNewListener {

    private SuggestAdapter suggestAdapter;
    private RecyclerView recyclerView;

    private UserDataHelper userDataHelper;

    private ImageButton hotelvoucher, foodvoucher;

    private CardView voucherCV, editProfileCV, mapCV, attractionCV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");

        recyclerView = view.findViewById(R.id.recycleViewSuuggest);
        userDataHelper = new UserDataHelper(getContext());

        hotelvoucher = view.findViewById(R.id.iv_hotel_voucher);
        foodvoucher = view.findViewById(R.id.iv_food_voucher);
        voucherCV = view.findViewById(R.id.cv_voucher);
        editProfileCV = view.findViewById(R.id.cv_edit_profile);
        mapCV = view.findViewById(R.id.cv_map);
        attractionCV = view.findViewById(R.id.cv_place);

        voucherCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VoucherActivity.class));
            }
        });

        editProfileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        mapCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsFragment()).commit();

            }
        });

        attractionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttractionFragment()).commit();
            }
        });

        hotelvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),HotelVoucherActivity.class));
            }
        });

        foodvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),FoodVoucherActivity.class));
            }
        });

        loadRecommend();

    }

    public void onNewCLick(int position,ImageView imageView) {
        Uri uri = Uri.parse(imageView.getContentDescription().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.putExtra("url", imageView.getContentDescription());
        startActivity(intent);
        Log.d("String","clicked" + position + imageView.getContentDescription());
    }

    public void loadRecommend(){
        try{

            ArrayList<Suggestion> getList = userDataHelper.getSuggest();

            if(getList != null) {
                suggestAdapter = new SuggestAdapter(getList,getActivity().getApplicationContext(),this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(suggestAdapter);
            }

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public void loadPlace(){
        try{
            SetOnClickLister();
            List<Place> getList = userDataHelper.getPlace();

            if(getList != null) {
                travelAdapter = new TravelAdapter(onClickListener,getActivity().getApplicationContext(),getList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView2.setLayoutManager(layoutManager);
                recyclerView2.setHasFixedSize(true);
                recyclerView2.setItemAnimator(new DefaultItemAnimator());
                recyclerView2.setAdapter(travelAdapter);
            }

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void SetOnClickLister() {
        onClickListener = new TravelAdapter.onClickListener() {
            @Override
            public void onClickListener(View v, int position) {
                Toast.makeText(getContext(), "CLicked",Toast.LENGTH_SHORT).show();
                
                
            }
        };
    }*/
}