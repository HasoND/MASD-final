package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class VoucherActivity extends AppCompatActivity {

    ArrayList<Voucher> voucherList;
    LinearLayoutManager layoutManager;

    private UserDataHelper userDataHelper;
    private VoucherAdapter VoucherAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        recyclerView = findViewById(R.id.recycleViewVoucher);
        userDataHelper = new UserDataHelper(this);

        loadHotelVoucher();

    }

    public void loadHotelVoucher(){
        try{
            voucherList = userDataHelper.getVoucher();

            if(voucherList != null) {
                VoucherAdapter = new VoucherAdapter(voucherList,this);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(VoucherAdapter);
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}