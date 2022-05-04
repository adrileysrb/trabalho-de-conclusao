package com.ifgoiano.mvipattern.api

import androidx.lifecycle.LiveData
import com.ifgoiano.mvipattern.model.Data
import com.ifgoiano.mvipattern.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(" ")
    fun getCountries(@Query("filter") query : String): LiveData<GenericApiResponse<List<Data>>>

}