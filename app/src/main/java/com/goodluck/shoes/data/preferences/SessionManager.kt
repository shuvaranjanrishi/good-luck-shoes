package com.goodluck.shoes.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.goodluck.shoes.data.models.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

context: Context.() -> DataStore<Preferences> private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    private val context: Context
) {
    private val userIdKey = longPreferencesKey("user_id")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")
    private val userRoleKey = stringPreferencesKey("user_role")

    suspend fun saveSession(
        userId: Long,
        userName: String,
        userEmail: String,
        userRole: UserRole
    ) {
        context.dataStore.edit { preferences ->
            preferences[userIdKey] = userId
            preferences[userNameKey] = userName
            preferences[userEmailKey] = userEmail
            preferences[userRoleKey] = userRole.name
        }
    }

    fun getUserId(): Flow<Long?> = context.dataStore.data.map { it[userIdKey] }

    fun getUserEmail(): Flow<String?> = context.dataStore.data.map { it[userEmailKey] }

    fun getUserRole(): Flow<UserRole?> = context.dataStore.data.map {
        it[userRoleKey]?.let { role -> UserRole.valueOf(role) }
    }

    fun isLoggedIn(): Flow<Boolean> = context.dataStore.data.map { it[userIdKey] != null }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
