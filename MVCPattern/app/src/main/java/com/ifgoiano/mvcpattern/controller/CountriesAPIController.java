package com.ifgoiano.mvcpattern.controller;

import android.util.Log;

import com.ifgoiano.mvcpattern.controller.api.CountriesAPIService;
import com.ifgoiano.mvcpattern.model.Country;
import com.ifgoiano.mvcpattern.view.APIActivity;
import com.ifgoiano.mvcpattern.view.SQLActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


//Essas classes que tão com controler no final são as que estão pegando os dados
//O nome no inicio indica da onde tá vindo os dados (Tirando o "Countries")
public class CountriesAPIController {

    private APIActivity view;
    private CountriesAPIService service;

    public CountriesAPIController(APIActivity view) {
        this.view = view;
        service = new CountriesAPIService();
        fetchCountries();
    }

    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> value) {
                        List<String> countryNames = new ArrayList<>();
                        for(Country country: value) {
                            countryNames.add(country.countryName);
                        }
                        view.setValues(countryNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });
    }

    public void onRefresh() {
        fetchCountries();
    }

}
