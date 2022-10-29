package br.com.alura.helloapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalhesContatoViewlModel(
    private val contatoDao: ContatoDao,
    private val idContato: Long,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalhesContatoUiState())
    val uiState: StateFlow<DetalhesContatoUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaContato()
        }
    }

    private suspend fun carregaContato() {
        contatoDao.buscaPorId(idContato).collect {
            it?.let { contatoEncontrado ->
                with(contatoEncontrado) {
                    _uiState.value = _uiState.value.copy(
                        id = id,
                        nome = nome,
                        sobrenome = sobrenome,
                        telefone = telefone,
                        fotoPerfil = fotoPerfil,
                        aniversario = aniversario
                    )
                }
            }
        }
    }

    fun removeContato() {
        viewModelScope.launch {
            contatoDao.remove(idContato)
        }
    }
}
