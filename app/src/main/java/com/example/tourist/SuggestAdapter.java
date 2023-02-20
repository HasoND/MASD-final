package com.example.tourist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {

    private ArrayList<Suggestion> suggestionList;
    private Context context;
    onNewListener onNewListener;

    public SuggestAdapter(ArrayList<Suggestion> suggestionList, Context context, SuggestAdapter.onNewListener onNewListener) {
        this.suggestionList = suggestionList;
        this.context = context;
        this.onNewListener = onNewListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.suggest_label,parent,false),onNewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Suggestion objectNews = suggestionList.get(position);
        holder.imageView.setContentDescription(objectNews.getSuggestUrl());
        holder.textView.setText(objectNews.getTitle());
        Picasso.with(context).load(objectNews.getSuggestThrumb()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        onNewListener onNewListener;

        public ViewHolder(@NonNull View itemView, onNewListener onNewListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_suggest);
            textView = itemView.findViewById(R.id.tv_suggest_title);
            this.onNewListener = onNewListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNewListener.onNewCLick(getAdapterPosition(),imageView);
        }
    }

    public interface onNewListener{
        void onNewCLick(int position,ImageView imageView);
    }

}
