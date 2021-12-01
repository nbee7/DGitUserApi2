package com.dgituserapi2.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreference private constructor(private val datastore: DataStore<Preferences>) {

    private val THEMEKEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return datastore.data.map { preferences ->
            preferences[THEMEKEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        datastore.edit { preferences ->
            preferences[THEMEKEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreference? = null

        fun getInstance(datastore: DataStore<Preferences>): ThemePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePreference(datastore)
                INSTANCE = instance
                instance
            }
        }
    }

}