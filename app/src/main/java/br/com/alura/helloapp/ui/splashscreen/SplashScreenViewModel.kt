package br.com.alura.helloapp.ui.splashscreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.util.preferences.PreferencesKeys.LOGADO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState: StateFlow<SplashScreenUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.data.collect { preferences ->
                if (preferences[LOGADO] == true) {
                    _uiState.value = _uiState.value.copy(
                        destino_inicial = DestinosHelloApp.HomeGraph.rota
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        destino_inicial = DestinosHelloApp.LoginGraph.rota
                    )
                }

                _uiState.value = _uiState.value.copy(carregando = false)
            }
        }
    }
}