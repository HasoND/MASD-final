package com.example.tourist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class AttractionFragment extends Fragment {

    private Button placeButton, foodButton;

    private RecyclerView recyclerView2;
    private TravelAdapter travelAdapter;
    TravelAdapter.onClickListener onClickListener;

    List<Place> placeList;

    private UserDataHelper userDataHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attractions & Foods");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        recyclerView2 = view.findViewById(R.id.place_food_recycleview);
        userDataHelper = new UserDataHelper(getContext());

        placeButton = view.findViewById(R.id.bt_place);
        foodButton = view.findViewById(R.id.bt_food);

        loadPlace();

        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlace();
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFood();
            }
        });


    }

    public void loadPlace(){
        try{
            SetOnClickLister();
            placeList = userDataHelper.getPlace();

            if(placeList != null) {
                travelAdapter = new TravelAdapter(onClickListener,getActivity().getApplicationContext(),placeList);
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

                Intent intent = new Intent(getContext(), TravelDetailActivity.class);
                intent.putExtra("img", placeList.get(position).getImage());
                intent.putExtra("name", placeList.get(position).getName());
                intent.putExtra("description", placeList.get(position).getDescription());
                intent.putExtra("category", placeList.get(position).getCategory());
                intent.putExtra("address", placeList.get(position).getAddress());
                intent.putExtra("video", placeList.get(position).getVideo());
                startActivity(intent);

            }
        };
    }

    public void loadFood(){
        try{
            SetFoodOnClickLister();
            placeList = userDataHelper.getFood();

            if(placeList != null) {
                travelAdapter = new TravelAdapter(onClickListener,getActivity().getApplicationContext(),placeList);
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

    public void SetFoodOnClickLister() {
        onClickListener = new TravelAdapter.onClickListener() {
            @Override
            public void onClickListener(View v, int position) {

                Intent intent = new Intent(getContext(), TravelDetailActivity.class);
                intent.putExtra("img", placeList.get(position).getImage());
                intent.putExtra("name", placeList.get(position).getName());
                intent.putExtra("description", placeList.get(position).getDescription());
                intent.putExtra("category", placeList.get(position).getCategory());
                intent.putExtra("address", placeList.get(position).getAddress());
                intent.putExtra("video", placeList.get(position).getVideo());
                startActivity(intent);

            }
        };
    }
}