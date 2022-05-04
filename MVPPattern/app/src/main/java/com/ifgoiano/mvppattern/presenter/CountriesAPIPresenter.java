package com.ifgoiano.mvppattern.presenter;

import android.util.Log;

import com.ifgoiano.mvppattern.model.Country;
import com.ifgoiano.mvppattern.presenter.api.CountriesAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesAPIPresenter {
    private View view;
    private CountriesAPIService service;

    public CountriesAPIPresenter(View view) {
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

    public interface View {
        void setValues(List<String> countries);
        void onError();
    }
}
