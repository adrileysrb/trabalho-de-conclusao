package com.ifgoiano.mvppattern.presenter.api;


import com.ifgoiano.mvppattern.model.Country;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {
    @GET(" ")
    Single<List<Country>> getCountries();
}
