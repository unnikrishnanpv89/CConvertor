package com.open.exchange.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sharedPreferenceStore by preferencesDataStore("exchange_pref")


class SharedPrefenceStore(context: Context) {
    private val dataStore = context.sharedPreferenceStore


    suspend fun saveStringToDataStore(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getStringValueFromDataStore(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preference ->
            preference[key] ?: String()
        }
    }

    fun getBoolValueFromDataStore(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[key] ?: true
        }
    }


    fun getLongValueFromDataStore(key: Preferences.Key<Long>): Flow<Long> {
        return dataStore.data.map { preference ->
            preference[key] ?: 0L
        }
    }

    suspend fun saveLongValueToDataStore(key: Preferences.Key<Long>, value: Long) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun saveBoolToDataStore(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {
        val IS_FIRSTTIME = booleanPreferencesKey("first_time")
        val DEFAULT_CURRENCY = stringPreferencesKey("default_currency")
        val RESPONSE_TIMESTAMP = longPreferencesKey("timestamp")
    }
}