package br.com.alura.helloapp.ui.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FormularioLoginViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioLoginUiState())
    val uiState: StateFlow<FormularioLoginUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        nome = it
                    )
                },
                onUsuarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        usuario = it
                    )
                },
                onSenhaMudou = {
                    _uiState.value = _uiState.value.copy(
                        senha = it
                    )
                },
            )
        }
    }

    suspend fun salvarLogin() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USUARIO] =
                _uiState.value.usuario
            preferences[PreferencesKey.SENHA] =
                _uiState.value.senha
        }
    }
}
