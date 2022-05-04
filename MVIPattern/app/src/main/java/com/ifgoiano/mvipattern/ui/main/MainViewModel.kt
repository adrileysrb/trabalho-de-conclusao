package com.ifgoiano.mvipattern.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ifgoiano.mvipattern.model.Data
import com.ifgoiano.mvipattern.repository.Repository
import com.ifgoiano.mvipattern.ui.main.state.MainStateEvent
import com.ifgoiano.mvipattern.ui.main.state.MainStateEvent.*
import com.ifgoiano.mvipattern.ui.main.state.MainViewState
import com.ifgoiano.mvipattern.util.AbsentLiveData
import com.ifgoiano.mvipattern.util.DataState

class MainViewModel : ViewModel(){

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState


    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>>{
        println("DEBUG: New StateEvent detected: $stateEvent")
        when(stateEvent){

            is GetCountriesEvent -> {
                return Repository.getBlogPosts()
            }

            is None ->{
                return AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPosts: List<Data>){
        val update = getCurrentViewStateOrNew()
        update.dataList = blogPosts
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let{
            it
        }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent){
        val state: MainStateEvent
        state = event
        _stateEvent.value = state
    }
}













