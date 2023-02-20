package com.example.tourist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotelVoucherAdapter extends RecyclerView.Adapter<HotelVoucherAdapter.ViewHolder> {

    private ArrayList<Voucher> voucherVoucher;
    private Context context;
    private onClickLister onClickLister;

    public HotelVoucherAdapter(ArrayList<Voucher> voucherVoucher, Context context, HotelVoucherAdapter.onClickLister onClickLister) {
        this.voucherVoucher = voucherVoucher;
        this.context = context;
        this.onClickLister = onClickLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_voucher_label,parent,false),onClickLister);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voucher voucher = voucherVoucher.get(position);
        holder.hotel.setText(voucher.getTitle());
        holder.discount.setText(voucher.getDiscount());

    }

    @Override
    public int getItemCount() {
        return voucherVoucher.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView hotel, discount;
        onClickLister onClickLister;

        public ViewHolder(@NonNull View itemView, onClickLister onClickLister) {
            super(itemView);

            hotel = itemView.findViewById(R.id.tv_voucher_hotel);
            discount = itemView.findViewById(R.id.tv_voucher_discount);

            this.onClickLister = onClickLister;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickLister.onClickLister(view, getAdapterPosition());
        }
    }

    public interface onClickLister{
        void onClickLister(View v, int position);
    }
}
