package com.coppel.technicaltest.ui.screens.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.domain.model.LocationModel
import com.coppel.technicaltest.domain.usercase.GetFactsLocalUseCase
import com.coppel.technicaltest.domain.usercase.GetFactsRemoteUseCase
import com.coppel.technicaltest.domain.usercase.InsertFactsLocalUseCase
import com.coppel.technicaltest.domain.usercase.SetBiometricCheckUseCase
import com.coppel.technicaltest.utils.LocationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val setBiometricCheckUseCase: SetBiometricCheckUseCase,
    private val getFactsLocalUseCase: GetFactsLocalUseCase,
    private val getFactsRemoteUseCase: GetFactsRemoteUseCase,
    private val insertFactsLocalUseCase: InsertFactsLocalUseCase,
    private val locationUtils: LocationUtils
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

    fun openFactDialog(fact: FactModel) {
        _homeState.value = _homeState.value.copy(selectedFact = fact)
    }

    fun closeFactDialog() {
        _homeState.value = _homeState.value.copy(selectedFact = null)
    }

    fun closeErrorDialog() {
        _homeState.value = _homeState.value.copy(locationState = LocationState.Loading)
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

    fun onPhoneChanged(phone: String) {
        _homeState.value = _homeState.value.copy(
            phoneNumber = phone,
            phoneSuppText = ""
        )
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

    fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("es", "MX"))
        return sdf.format(Date(timestamp))
    }

    fun hasLocationPermission(): Boolean {
        return locationUtils.hasLocationPermission()
    }

    fun initLocation() {
        val phoneNumber = _homeState.value.phoneNumber
        if (phoneNumber.length < 10) {
            _homeState.value = _homeState.value.copy(
                phoneSuppText = "Phone number must be at least 10 digits"
            )
            return
        }
        if (locationUtils.isInternetAvailable()) {
            viewModelScope.launch {
                _homeState.value = _homeState.value.copy(locationState = LocationState.Loading)
                requestLocationUpdate()
            }
        } else {
            _homeState.value = _homeState.value.copy(locationState = LocationState.InternetError)
            closeFactDialog()
        }
    }

    private fun requestLocationUpdate() {
        viewModelScope.launch {
            locationUtils.requestLocation()
                .collect { newLocation: Pair<LocationModel, String> ->
                    if (newLocation.first.latitude == 0.0 || newLocation.first.longitude == 0.0) {
                        _homeState.value = _homeState.value.copy(
                            locationState = LocationState.Error,
                            errorMessage = newLocation.second
                        )
                    } else {
                        _homeState.value = _homeState.value.copy(
                            locationState = LocationState.Success,
                            location = newLocation.first
                        )
                        if (_homeState.value.selectedFact != null && _homeState.value.location != null) {
                            val message =
                                generateFactMessage(_homeState.value.selectedFact!!,
                                    _homeState.value.location!!
                                )
                            val phone = "52"+_homeState.value.phoneNumber
                            sendMessageToWhatsApp(phone, message) {
                                closeFactDialog()
                                _homeState.value = _homeState.value.copy(
                                    phoneNumber = "",
                                    phoneSuppText = ""
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun generateFactMessage(fact: FactModel, location: LocationModel): String {
        return """
        📌 *Selected Fact*

        🆔 *UID:* ${fact.uid}
        📅 *Insert Date:* ${fact.dateInsert}
        🏷️ *Slug:* ${fact.slug}
        🧩 *Columns:* ${fact.columns}
        📊 *Fact:* ${fact.fact}
        🏢 *Organization:* ${fact.organization}
        🔗 *Resource:* ${fact.resource}
        🌐 *URL:* ${fact.url}
        ⚙️ *Operations:* ${fact.operations}
        📁 *Dataset:* ${fact.dataset}
        🕒 *Created At:* ${formatTimestamp(fact.createdAt)}
        📍 *Latitude:* ${location.latitude}
        📍 *Longitude:* ${location.longitude}
        🗺️ [Open in Maps](https://www.google.com/maps/search/?api=1&query=${location.latitude},${location.longitude})
    """.trimIndent()
    }

    private fun sendMessageToWhatsApp(
        phoneNumber: String,
        message: String,
        onSuccess: (() -> Unit)? = null
    ) {
        val uri = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            onSuccess?.invoke()
        } else {
            _homeState.value = _homeState.value.copy(
                phoneSuppText = "WhatsApp is not installed"
            )
            Log.e("sendMessageToWhatsApp", "WhatsApp is not installed")
        }
    }

}