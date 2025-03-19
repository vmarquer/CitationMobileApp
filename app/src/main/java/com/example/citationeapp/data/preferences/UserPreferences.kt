package com.example.citationeapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.citationeapp.viewmodel.CitationVersion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val KEY_VERSION = stringPreferencesKey("citation_version")
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun saveVersion(version: CitationVersion) {
        dataStore.edit { preferences ->
            preferences[KEY_VERSION] = version.name
        }
    }

    val version: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_VERSION] ?: CitationVersion.VF.name
    }

    suspend fun saveAuthToken(token: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_AUTH_TOKEN]
    }

    val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_REFRESH_TOKEN]
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_AUTH_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }

    val isAuthenticated: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[KEY_AUTH_TOKEN]?.isNotEmpty() == true
    }
}
