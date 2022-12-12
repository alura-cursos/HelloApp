package br.com.alura.helloapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao
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
    }

    suspend fun buscarParcialmente(valor: String) {
        contatoDao.buscaParcial(valor).collect { contatosBuscados ->
            _uiState.value = _uiState.value.copy(
                contatos = contatosBuscados
            )
        }
    }

}