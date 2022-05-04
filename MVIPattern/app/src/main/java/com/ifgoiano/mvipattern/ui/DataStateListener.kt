package com.ifgoiano.mvipattern.ui

import com.ifgoiano.mvipattern.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}