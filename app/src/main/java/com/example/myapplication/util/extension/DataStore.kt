package com.example.myapplication.util.extension

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStore() {
    private val Context.dataStore by preferencesDataStore("DataStore")
    object PreferencesKey {
        val SessionIdKey = stringPreferencesKey("mySessionId")
    }
    suspend fun readFromDataStore(context: Context): String? {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                println("Failed get data!")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[PreferencesKey.SessionIdKey]
        }.firstOrNull()
    }
    suspend fun writeToDataStore(context: Context, data: String) {
        context.dataStore.edit {
            it[PreferencesKey.SessionIdKey] = data
            println("Data stored!")
        }
    }
}
