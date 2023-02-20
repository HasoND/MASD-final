package com.example.tourist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    private ArrayList<Voucher> voucher;
    private Context context;

    public VoucherAdapter(ArrayList<Voucher> voucher, Context context) {
        this.voucher = voucher;
        this.context = context;;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoucherAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_voucher_label,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voucher voucher = this.voucher.get(position);
        holder.hotel.setText(voucher.getTitle());
        holder.discount.setText(voucher.getDiscount());
    }

    @Override
    public int getItemCount() {
        return voucher.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hotel, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotel = itemView.findViewById(R.id.tv_voucher_hotel);
            discount = itemView.findViewById(R.id.tv_voucher_discount);
        }
    }
}
