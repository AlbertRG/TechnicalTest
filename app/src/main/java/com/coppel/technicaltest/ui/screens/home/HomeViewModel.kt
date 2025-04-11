package com.coppel.technicaltest.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> get() = _homeState

    fun onSearchQueryChanged(newQuery: String) {
        _homeState.value = _homeState.value.copy(searchQuery = newQuery)
    }

}