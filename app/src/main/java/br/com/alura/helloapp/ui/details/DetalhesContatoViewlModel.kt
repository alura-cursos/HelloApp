package br.com.alura.helloapp.ui.details

import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.database.ContatoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetalhesContatoViewlModel(
    private val contatoDao: ContatoDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalhesContatoUiState())
    val uiState: StateFlow<DetalhesContatoUiState>
        get() = _uiState.asStateFlow()

    suspend fun carregaContato(idContato: Long) {
        contatoDao.buscaPorId(idContato)?.let { contatoEncontrado ->
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

    suspend fun removeContato() {
        contatoDao.remove(_uiState.value.id)
    }
}
