package br.com.alura.helloapp.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaDate
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.database.ContatoDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FormularioContatoViewModel(
    private val contatoDao: ContatoDao,
    private val idContato: Long,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioContatoUiState())
    val uiState: StateFlow<FormularioContatoUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaContato()
        }

        _uiState.update { state ->

            state.copy(

                tituloAppbar = if (idContato == 0L) {
                    R.string.titulo_activity_cadastro_contato
                } else R.string.titulo_activity_editar_contato,

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
                        contato = _uiState.value.contato.copy(aniversario = it.converteParaDate())
                    )
                    fecharCaixaData()
                },

                )
        }

    }

    private suspend fun carregaContato() {
        contatoDao.buscaPorId(idContato).collect {
            it?.let { contatoEncontrado ->
                _uiState.value = _uiState.value.copy(
                    contato = contatoEncontrado
                )
            }
        }
    }

    fun salvarContato() {
        viewModelScope.launch {
            contatoDao.insere(_uiState.value.contato)
        }
    }

    fun defineTextoAniversario(textoAniversario: String): String {
        _uiState.value.contato.aniversario?.let {
            return it.converteParaString()
        }
        return textoAniversario
    }

    fun carregarImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            _uiState.value.contato.copy(fotoPerfil = url)
        )
        fecharCaixaImagem()
    }


    fun mostrarCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = true)
        }
    }

    fun fecharCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = false)
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