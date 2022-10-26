package br.com.alura.helloapp.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaDate
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.util.AuxilarStringResource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CadastroContatoViewModel(
    private val contatoDao: ContatoDao,
    private val idContato: Long,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CadastroContatoUiState())
    val uiState: StateFlow<CadastroContatoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaContato()
        }

        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        _uiState.value.contato.copy(nome = it)
                    )
                },
                onSobrenomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        _uiState.value.contato.copy(sobrenome = it)
                    )
                },
                onTelefoneMudou = {
                    _uiState.value = _uiState.value.copy(
                        _uiState.value.contato.copy(telefone = it)
                    )
                },
                onFotoPerfilMudou = {
                    _uiState.value = _uiState.value.copy(
                        _uiState.value.contato.copy(fotoPerfil = it)
                    )
                },
                onAniversarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        _uiState.value.contato.copy(aniversario = it.converteParaDate())
                    )
                    fecharCaixaData()
                },

                )
        }

    }

    private suspend fun carregaContato() {
        contatoDao.buscaPorId(idContato).collect {
            it?.let {
                _uiState.value = _uiState.value.copy(
                    contato = it.copy()
                )
            }
        }
    }

    fun defineTextoAniversario(stringResource: String): String {
        _uiState.value.contato.aniversario?.let {
            return it.converteParaString()
        }
        return stringResource
    }


    fun defineTituloAppBar(): AuxilarStringResource {
        return if (idContato == 0L) {
            AuxilarStringResource.StringResource(R.string.title_activity_cadastro_contato)
        } else AuxilarStringResource.StringResource(R.string.title_activity_editar_contato)
    }


    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            _uiState.value.contato.copy(fotoPerfil = url)
        )
        fecharCaixaImagem()
    }


    fun fecharCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = false)
        }
    }

    fun abriCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = true)
        }
    }

    fun mostrarCaixaData() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoData = true)
        }
    }

    fun fecharCaixaData() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoData = false)
        }
    }
}