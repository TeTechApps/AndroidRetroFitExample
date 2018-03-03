package com.fiskus.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private ArrayList<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
