package org.d3ifcool.virtualab.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val USER_TYPE = intPreferencesKey("user_type")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
    }

    val loginFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val typeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_TYPE] ?: -1
    }

    val idFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    suspend fun setUserType(userType: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_TYPE] = userType
        }
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }
    suspend fun setUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }
}