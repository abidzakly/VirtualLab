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
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.User

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class UserDataStore(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = intPreferencesKey("user_id")
        private val USER_FULLNAME = stringPreferencesKey("user_fullname")
        private val USER_USERNAME = stringPreferencesKey("user_username")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_TYPE = intPreferencesKey("user_type")
        private val USER_SCHOOL = stringPreferencesKey("user_school")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
        private val MURID_NISN = stringPreferencesKey("murid_nisn")
        private val GURU_NIP = stringPreferencesKey("guru_nip")
    }

    val loginStatusFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val userFlow: Flow<User> = context.dataStore.data.map {
        User(
            user_id = it[USER_ID] ?: -1,
            full_name = it[USER_FULLNAME] ?: "",
            email = it[USER_EMAIL] ?: "",
            username = it[USER_USERNAME] ?: "",
            school = it[USER_SCHOOL] ?: "",
            user_type = it[USER_TYPE] ?: -1,
            password = it[USER_PASSWORD] ?: ""
        )
    }

    val nisnFlow: Flow<String> = context.dataStore.data.map {
            it[MURID_NISN] ?: ""
    }

    val nipFlow: Flow<String> = context.dataStore.data.map {
            it[GURU_NIP] ?: ""
    }

    val userIdFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: -1
    }

    val userFullNameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_FULLNAME] ?: ""
    }

    val userTypeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_TYPE] ?: -1
    }

    val userEmailFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL] ?: ""
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveData(user: User, murid: Murid? = null, guru: Guru? = null) {
        context.dataStore.edit {
            it[USER_ID] = user.user_id
            it[USER_FULLNAME] = user.full_name
            it[USER_EMAIL] = user.email
            it[USER_PASSWORD] = user.password
            it[USER_SCHOOL] = user.school
            it[USER_USERNAME] = user.username
            it[USER_TYPE] = user.user_type
            if (murid != null) {
                it[MURID_NISN] = murid.nisn
            } else if (guru != null) {
                it[GURU_NIP] = guru.nip
            }
        }
    }
}