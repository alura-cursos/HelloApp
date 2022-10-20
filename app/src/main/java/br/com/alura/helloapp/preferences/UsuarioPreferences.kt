package br.com.alura.helloapp.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "infos_login")

object PreferencesKeys {
    val NOME_USUARIO = stringPreferencesKey("nome_Usuario")
    val USUARIO_LOGADO = booleanPreferencesKey("usuario_logado")

}