package com.coppel.technicaltest.ui.screens.home

import com.coppel.technicaltest.domain.model.FactModel

data class HomeState(
    val homeState: HomeUiState = HomeUiState.Success,
    val factList: List<FactModel> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String = "Ops! Something went wrong"
)

sealed class HomeUiState(val name: String) {
    data object Loading : HomeUiState(name = "Loading")
    data object Success : HomeUiState(name = "Success")
    data object Error : HomeUiState(name = "Error")

}