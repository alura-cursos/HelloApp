package br.com.alura.helloapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.util.ID_CONTATO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalhesContatoViewlModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val contatoDao: ContatoDao
) : ViewModel() {

    private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(DetalhesContatoUiState())
    val uiState: StateFlow<DetalhesContatoUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaContato()
        }
    }

    private suspend fun carregaContato() {
        idContato?.let {
            val contato = contatoDao.buscaPorId(idContato)
            contato.collect {
                it?.let {
                    with(it) {
                        _uiState.value = _uiState.value.copy(
                            id = id,
                            nome = nome,
                            sobrenome = sobrenome,
                            aniversario = aniversario,
                            telefone = telefone,
                            fotoPerfil = fotoPerfil
                        )
                    }
                }
            }
        }
    }

    suspend fun removeContato() {
        idContato?.let { contatoDao.deleta(it) }
    }
}
