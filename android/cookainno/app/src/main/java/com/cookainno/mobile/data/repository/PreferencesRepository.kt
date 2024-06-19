package com.cookainno.mobile.data.repository

import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.cookainno.mobile.MainActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store_prefs")

class PreferencesRepository(private val context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveToken(value: String) {
        val dataStoreKey = stringPreferencesKey(USER_TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    suspend fun deleteToken() {
        val dataStoreKey = stringPreferencesKey(USER_TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences.remove(dataStoreKey)
        }
        restartApp()
    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        Runtime.getRuntime().exit(0)
    }

    companion object {
        private const val USER_TOKEN_KEY = "user_token"
    }

    fun getTokenFlow(): Flow<String?> {
        val dataStoreKey = stringPreferencesKey(USER_TOKEN_KEY)
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey]
        }
    }
}

