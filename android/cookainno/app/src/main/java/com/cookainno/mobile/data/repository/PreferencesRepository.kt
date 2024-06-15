package com.cookainno.mobile.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store_prefs")

class PreferencesRepository(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveData(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    val getData: (String) -> Flow<String?> = { key ->
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.data
            .map { preferences ->
                preferences[dataStoreKey]
            }
    }
}
