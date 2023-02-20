package com.example.tourist;

import android.graphics.Bitmap;

public class Place {


    private String image;
    private String name, description, category, address, video;

    public Place(String image, String name, String description, String category, String address, String video) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
