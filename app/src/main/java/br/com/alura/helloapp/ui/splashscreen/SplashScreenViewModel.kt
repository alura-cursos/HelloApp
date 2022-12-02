package br.com.alura.helloapp.ui.splashscreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.preferences.PreferencesKey.LOGADO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState: StateFlow<SplashScreenUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            definiDestinoInicial()
        }
    }

    private suspend fun definiDestinoInicial() {
        delay(1000)
        dataStore.data.collect {
            val appState = if (it[LOGADO] == true) {
                AppState.Logado
            } else {
                AppState.Deslogado
            }

            _uiState.value = _uiState.value.copy(
                appState = appState
            )
        }
    }
}