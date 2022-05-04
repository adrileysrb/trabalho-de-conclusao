package com.ifgoiano.mvipattern.api

import com.ifgoiano.mvipattern.util.Values
import com.ifgoiano.mvipattern.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {

     const val URL: String = "http://ccdb.hemiola.com/characters/"

    val retrofitBuilder: Retrofit.Builder by lazy {

        Retrofit.Builder()
            .baseUrl(URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

    }


    val apiService: ApiService by lazy{
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}