package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val contatos = contatoDao.buscaTodos()
            contatos.collect { contatosBuscados ->
                _uiState.value = _uiState.value.copy(
                    contatos = contatosBuscados
                )
            }
        }

        viewModelScope.launch {
            carregaNomeUsuario()
        }

        _uiState.update { state ->
            state.copy(
                onMostrarCaixaDialogoDeslogarMudou = {
                    _uiState.value = _uiState.value.copy(
                        mostrarCaixaDialogoDeslogar = it
                    )
                }
            )
        }
    }

    private suspend fun carregaNomeUsuario() {
        val usuario = dataStore.data.first()[PreferencesKey.USUARIO]
        usuario?.let {
            _uiState.value = _uiState.value.copy(
                nomeUsuario = it
            )
        }
    }

}