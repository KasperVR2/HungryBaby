package com.example.hungrybaby.ui.opening

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StartData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("babyData")
        private val BABY_DATA = stringPreferencesKey("babyData")
    }

    val getBabyData: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[BABY_DATA] ?: ""
        }

    suspend fun saveBabyData(token: String) {
        context.dataStore.edit { preferences ->
            preferences[BABY_DATA] = token
        }
    }
}
