package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodVoucherActivity extends AppCompatActivity {

    ArrayList<Voucher> voucherList;
    LinearLayoutManager layoutManager;

    private UserDataHelper userDataHelper;
    private HotelVoucherAdapter hotelVoucherAdapter;
    private RecyclerView recyclerView;

    HotelVoucherAdapter.onClickLister onClickLister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_voucher);

        recyclerView = findViewById(R.id.recycleViewFood);
        userDataHelper = new UserDataHelper(this);

        loadFoodVoucher();
    }

    public void loadFoodVoucher(){
        try{
            SetOnClickLister();
            voucherList = userDataHelper.getFoodVoucher();
            Log.d("food", voucherList.get(1).getDiscount());
            if(voucherList != null) {
                hotelVoucherAdapter = new HotelVoucherAdapter(voucherList,this,  onClickLister);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(hotelVoucherAdapter);
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void SetOnClickLister() {
        onClickLister = new HotelVoucherAdapter.onClickLister() {
            @Override
            public void onClickLister(View v, int position) {

                voucherList.get(position).getId();
                userDataHelper.updateFoodVoucher(voucherList.get(position).getId());

                hotelVoucherAdapter = new HotelVoucherAdapter(voucherList,getApplication(),  onClickLister);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(hotelVoucherAdapter);
                Toast.makeText(getApplication(),"You get a new voucher.",Toast.LENGTH_SHORT).show();
            }
        };
    }
}