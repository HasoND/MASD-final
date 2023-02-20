package com.example.tourist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotelVoucherActivity extends AppCompatActivity {

    ArrayList<Voucher> voucherList;
    LinearLayoutManager layoutManager;
    private UserDataHelper userDataHelper;
    private HotelVoucherAdapter hotelVoucherAdapter;
    private RecyclerView recyclerView;

    HotelVoucherAdapter.onClickLister onClickLister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_voucher);

        recyclerView = findViewById(R.id.recycleViewHotel);
        userDataHelper = new UserDataHelper(this);

        loadHotelVoucher();

    }

    public void loadHotelVoucher(){
        try{
            SetOnClickLister();
            voucherList = userDataHelper.getHotelVoucher();
            Log.d("hotellist", voucherList.toString());
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
                userDataHelper.updateHotelVoucher(voucherList.get(position).getId());

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
