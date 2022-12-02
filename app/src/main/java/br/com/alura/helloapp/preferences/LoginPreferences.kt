package br.com.alura.helloapp.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

object PreferencesKey{
    val SENHA = stringPreferencesKey("senha")
    val USUARIO = stringPreferencesKey("usuario")
    val LOGADO = booleanPreferencesKey("logado")
}