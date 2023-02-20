package com.example.tourist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class TravelDetailActivity extends AppCompatActivity {

    private YouTubePlayerView videoView;
    private ImageButton copyButton;
    private ImageView imageView;
    private TextView titles, descriptions, categorys, addresses;

    UserDataHelper userDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);

        Bundle getlist = getIntent().getExtras();
        String img = getlist.getString("img");
        String name = getlist.getString("name");
        String description = getlist.getString("description");
        String category = getlist.getString("category");
        String address = getlist.getString("address");
        String video = getlist.getString("video");

        userDataHelper = new UserDataHelper(this);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);

        byte [] imageBytes = Base64.decode(img, Base64.DEFAULT);
        Bitmap imges = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        titles = findViewById(R.id.tv_detail_name);
        descriptions = findViewById(R.id.tv_detail_description);
        categorys = findViewById(R.id.tv_detail_category);
        addresses = findViewById(R.id.tv_detail_address);
        imageView = findViewById(R.id.iv_detail_image);

        copyButton = findViewById(R.id.ib_detail_copy);
        videoView = findViewById(R.id.yt_video);
        getLifecycle().addObserver(videoView);

        titles.setText(name);
        descriptions.setText(description);
        categorys.setText(category);
        addresses.setText(address);

        if(video != null && category.equals("place")) {
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
        }else{
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(imges);
        }

        videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if(video != null && category.equals("place")) {
                    youTubePlayer.loadVideo(video, 0);
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("address", address);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Address copied...",Toast.LENGTH_SHORT).show();
            }
        });

    }
}