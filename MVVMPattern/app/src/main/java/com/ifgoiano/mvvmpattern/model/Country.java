package com.ifgoiano.mvvmpattern.model;

import com.google.gson.annotations.SerializedName;

public class Country {

    private String id;

    @SerializedName("string")
    public String countryName;

    public Country(){}

    public Country(String name){
        this.countryName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
