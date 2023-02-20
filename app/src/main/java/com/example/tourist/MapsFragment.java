package com.example.tourist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private FusedLocationProviderClient client;
    private GoogleMap map;

    private SearchView search;
    private FloatingActionButton currLocation, expandButton, food, hotel, place, all;

    UserDataHelper userDataHelper;

    private boolean clicked = false;
    private LatLng tempLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        search = view.findViewById(R.id.search_location);
        currLocation = view.findViewById(R.id.ib_currentLocation);
        expandButton = view.findViewById(R.id.fb_expand);
        food = view.findViewById(R.id.fb_food);
        hotel = view.findViewById(R.id.fb_hotel);
        place = view.findViewById(R.id.fb_place);
        all = view.findViewById(R.id.fb_all);

        food.setOnClickListener(this);
        hotel.setOnClickListener(this);
        place.setOnClickListener(this);
        all.setOnClickListener(this);

        userDataHelper = new UserDataHelper(getContext());
        client = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mapFragment != null) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(@NonNull Location location) {
                        if (location != null) {
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(@NonNull GoogleMap googleMap) {
                                    map = googleMap;
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                    googleMap.addMarker(options);
                                    tempLocation = latLng;

                                    loadMarker("food");
                                    loadMarker("hotel");
                                    loadMarker("place");

                                    currLocation.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Address addressResult = searchAddress(getActivity(), query);
                if (addressResult != null) {
                    LatLng myLocation = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                    map.addMarker(new MarkerOptions().position(myLocation).title(addressResult.getLocality()));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisible(clicked);
                setAnimation(clicked);
                setClickable(clicked);
                clicked = !clicked;
            }
        });
    }


    private void setClickable(boolean clicked) {
        if (!clicked) {
            food.setClickable(true);
            hotel.setClickable(true);
            place.setClickable(true);
            all.setClickable(true);
        } else {
            food.setClickable(false);
            hotel.setClickable(false);
            place.setClickable(false);
            all.setClickable(false);
        }

    }

    private void setAnimation(boolean clicked) {
        Animation toBottom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.float_back);
        Animation fromBottom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.float_out);

        if (!clicked) {
            food.startAnimation(fromBottom);
            hotel.startAnimation(fromBottom);
            place.startAnimation(fromBottom);
            all.startAnimation(fromBottom);
        } else {
            food.startAnimation(toBottom);
            hotel.startAnimation(toBottom);
            place.startAnimation(toBottom);
            all.startAnimation(toBottom);
        }

    }

    private void setVisible(boolean clicked) {
        if (!clicked) {
            hotel.setVisibility(View.VISIBLE);
            food.setVisibility(View.VISIBLE);
            place.setVisibility(View.VISIBLE);
            all.setVisibility(View.VISIBLE);
        } else {
            hotel.setVisibility(View.INVISIBLE);
            food.setVisibility(View.INVISIBLE);
            place.setVisibility(View.INVISIBLE);
            all.setVisibility(View.INVISIBLE);
        }

    }

    public static Address searchAddress(Activity activity, String address) {
        try {
            Geocoder geocoder = new Geocoder(activity);
            List<Address> result = geocoder.getFromLocationName(address, 1);
            Address addressResult = result.get(0);

            Log.d("Address info: ", addressResult.toString());
            return addressResult;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    public void loadMarker(String type) {

        JSONArray jsonArray = userDataHelper.getMarker(type);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject marker = jsonArray.getJSONObject(i);
                LatLng newLocation = new LatLng(marker.getDouble("lat"), marker.getDouble("long"));
                if (type.equals("food"))
                    map.addMarker(new MarkerOptions().position(newLocation).title(type).icon(BitmapDescriptorFactory.fromResource(R.drawable.food_marker)));
                else if (type.equals("place"))
                    map.addMarker(new MarkerOptions().position(newLocation).title(type).icon(BitmapDescriptorFactory.fromResource(R.drawable.place_marker)));
                else
                    map.addMarker(new MarkerOptions().position(newLocation).title(type).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_all:
                Log.d("test",view.getId()+" ");
                map.clear();
                map.addMarker(new MarkerOptions().position(tempLocation).title("You are here"));
                loadMarker("hotel");
                loadMarker("food");
                loadMarker("place");
                break;

            case R.id.fb_food:
                map.clear();
                map.addMarker(new MarkerOptions().position(tempLocation).title("You are here"));
                loadMarker("food");
                break;

            case R.id.fb_hotel:
                map.clear();
                map.addMarker(new MarkerOptions().position(tempLocation).title("You are here"));
                loadMarker("hotel");
                break;

            case R.id.fb_place:
                map.clear();
                map.addMarker(new MarkerOptions().position(tempLocation).title("You are here"));
                loadMarker("place");
                break;

            default:
                map.addMarker(new MarkerOptions().position(tempLocation).title("You are here"));
                loadMarker("hotel");
                loadMarker("food");
                loadMarker("place");
                break;
        }
    }
}