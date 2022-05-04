package com.ifgoiano.mvipattern.ui.main.state

sealed class MainStateEvent {

    class GetCountriesEvent: MainStateEvent()

    class None: MainStateEvent()


}