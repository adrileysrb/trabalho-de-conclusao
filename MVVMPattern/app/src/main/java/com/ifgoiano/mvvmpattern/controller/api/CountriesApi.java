package com.ifgoiano.mvvmpattern.controller.api;


import com.ifgoiano.mvvmpattern.model.Country;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {
    @GET(" ")
    Single<List<Country>> getCountries();
}
