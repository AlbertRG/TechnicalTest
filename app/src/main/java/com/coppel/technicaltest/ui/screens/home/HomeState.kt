package com.coppel.technicaltest.ui.screens.home

import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.model.LocationModel

data class HomeState(
    val homeState: HomeUiState = HomeUiState.Loading,
    val locationState: LocationState = LocationState.Loading,
    val factList: List<FactModel> = emptyList(),
    val filterList: List<FactModel> = emptyList(),
    val searchQuery: String = "",
    val phoneNumber: String = "",
    val phoneSuppText: String = "",
    val isPhoneNumberError: Boolean = false,
    val selectedFact: FactModel? = null,
    val location: LocationModel? = null,
    val errorMessage: String = "Ops! Something went wrong"
)

sealed class HomeUiState(val name: String) {
    data object Loading : HomeUiState(name = "Loading")
    data object Success : HomeUiState(name = "Success")
    data object Error : HomeUiState(name = "Error")
}

sealed class LocationState(val name: String) {
    data object Loading : LocationState("Loading")
    data object Success : LocationState("Success")
    data object Error : LocationState("Error")
    data object InternetError : LocationState("InternetError")
}