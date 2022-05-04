package com.ifgoiano.mvipattern.repository

import androidx.lifecycle.LiveData
import com.ifgoiano.mvipattern.api.MyRetrofitBuilder
import com.ifgoiano.mvipattern.model.Data
import com.ifgoiano.mvipattern.ui.main.state.MainViewState
import com.ifgoiano.mvipattern.util.*

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Data>, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Data>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        dataList = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Data>>> {

                if(Values.BASE_URL_ID == 1)
                    return MyRetrofitBuilder.apiService.getCountries(Constants.LEVE)
                else if(Values.BASE_URL_ID == 2)
                    return MyRetrofitBuilder.apiService.getCountries(Constants.MEDIA)
                else
                    return MyRetrofitBuilder.apiService.getCountries("")
            }

        }.asLiveData()
    }
}




























