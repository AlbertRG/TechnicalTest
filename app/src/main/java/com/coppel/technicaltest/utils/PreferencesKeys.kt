package com.coppel.technicaltest.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {

    val BIOMETRIC_CHECK = booleanPreferencesKey("biometric_check")
    val FIRST_TIME = booleanPreferencesKey("first_time")

}