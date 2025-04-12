package com.coppel.technicaltest.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.technicaltest.domain.usercase.GetFactsLocalUseCase
import com.coppel.technicaltest.domain.usercase.GetFactsRemoteUseCase
import com.coppel.technicaltest.domain.usercase.InsertFactsLocalUseCase
import com.coppel.technicaltest.domain.usercase.SetBiometricCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val setBiometricCheckUseCase: SetBiometricCheckUseCase,
    private val getFactsLocalUseCase: GetFactsLocalUseCase,
    private val getFactsRemoteUseCase: GetFactsRemoteUseCase,
    private val insertFactsLocalUseCase: InsertFactsLocalUseCase
) : ViewModel() {

    init {
        loadFacts()
    }

    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> get() = _homeState

    private fun loadFacts() {
        viewModelScope.launch {
            val localFacts = getFactsLocalUseCase().first()
            Log.d("HomeViewModel", "Fetched ${localFacts.size} facts from local database.")
            if (localFacts.isNotEmpty()) {
                Log.d("HomeViewModel", "Using local facts.")
                _homeState.value = _homeState.value.copy(
                    factList = localFacts,
                    filterList = localFacts,
                    homeState = HomeUiState.Success
                )
            } else {
                Log.d(
                    "HomeViewModel",
                    "No local facts found. Waiting 4 seconds before fetching from remote..."
                )
                kotlinx.coroutines.delay(4000L)
                val remoteFacts = getFactsRemoteUseCase()
                Log.d("HomeViewModel", "Fetched ${remoteFacts.size} facts from remote source.")

                try {
                    insertFactsLocalUseCase(remoteFacts)
                    Log.d(
                        "HomeViewModel",
                        "Successfully inserted remote facts into local database."
                    )
                    _homeState.value = _homeState.value.copy(
                        factList = remoteFacts,
                        filterList = remoteFacts,
                        homeState = HomeUiState.Success
                    )
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Failed to insert remote facts into local database", e)
                }

            }
        }
    }

    fun signOut(navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            val success = setBiometricCheckUseCase(false)
            if (success) {
                Log.d("HomeViewModel", "BiometricCheck disabled successfully. Navigating to Login.")
                navigateToLogin()
            } else {
                Log.e("HomeViewModel", "Failed to disable biometric check. Navigation aborted.")
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _homeState.value = _homeState.value.copy(searchQuery = newQuery)
        filterFactList(newQuery)
    }

    private fun filterFactList(query: String) {
        val currentList = _homeState.value.factList
        if (query.isNotEmpty()) {
            val filteredList = currentList.filter { fact ->
                fact.organization.contains(query, ignoreCase = true)
            }
            _homeState.value = _homeState.value.copy(filterList = filteredList)
        } else {
            _homeState.value = _homeState.value.copy(filterList = currentList)
        }
    }

}