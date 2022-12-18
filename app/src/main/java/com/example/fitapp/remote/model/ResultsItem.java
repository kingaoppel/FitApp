package com.example.fitapp.remote.model;

import com.google.gson.annotations.SerializedName;

public class ResultsItem {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}