package com.example.tourist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolders> {

    private onClickListener onClickListener;
    private Context context;
    private List<Place> placeList;

    public TravelAdapter(TravelAdapter.onClickListener onClickListener, Context context, List<Place> placeList) {
        this.onClickListener = onClickListener;
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public TravelAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelAdapter.ViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_place_food_list,parent,false),onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        Place place = placeList.get(position);

        byte [] imageBytes = Base64.decode(place.getImage(), Base64.DEFAULT);
        Bitmap img = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        Log.d("imgblob", place.getImage()+" ");
        holder.imgView.setImageBitmap(img);
        holder.hotelName.setText(place.getName());
        holder.descriptionText.setText(place.getDescription());
        holder.categoryText.setText((place.getCategory()));
        holder.addressText.setText(place.getAddress());

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgView;
        TextView descriptionText, hotelName, addressText, categoryText;
        onClickListener onClickListener;
        public ViewHolders(@NonNull View itemView, onClickListener onClickListener) {
            super(itemView);

            imgView = itemView.findViewById(R.id.imageView);
            hotelName = itemView.findViewById(R.id.textViewPlaceName);
            descriptionText = itemView.findViewById(R.id.textViewShortDesc);
            categoryText = itemView.findViewById(R.id.textViewCategory);
            addressText = itemView.findViewById(R.id.textViewAddress);

            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            onClickListener.onClickListener(view, getAdapterPosition());
        }
    }

    public interface onClickListener{
        void onClickListener(View v, int position);
    }


}
