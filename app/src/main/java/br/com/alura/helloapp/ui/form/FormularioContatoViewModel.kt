package br.com.alura.helloapp.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.R
import br.com.alura.helloapp.extensions.converteParaDate
import br.com.alura.helloapp.extensions.converteParaString
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.ContatoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                        nome = it
                    )

                },
                onSobrenomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        sobrenome = it
                    )
                },
                onTelefoneMudou = {
                    _uiState.value = _uiState.value.copy(
                        telefone = it
                    )
                },
                onFotoPerfilMudou = {
                    _uiState.value = _uiState.value.copy(
                        fotoPerfil = it
                    )
                },
                onAniversarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        aniversario = it.converteParaDate()
                    )
                    fechaCaixaData()
                },
            )
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

    fun salvaContato() {
        viewModelScope.launch {
            with(_uiState.value) {
                contatoDao.insere(
                    Contato(
                        id = id,
                        nome = nome,
                        sobrenome = sobrenome,
                        telefone = telefone,
                        fotoPerfil = fotoPerfil,
                        aniversario = aniversario
                    )
                )
            }
        }
    }

    fun defineTextoAniversario(textoAniversario: String): String {
        _uiState.value.aniversario?.let {
            return it.converteParaString()
        }
        return textoAniversario
    }

    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = url
        )
        fechaCaixaImagem()
    }


    fun mostraCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = true)
        }
    }

    fun fechaCaixaImagem() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoImagem = false)
        }
    }

    fun mostraCaixaData() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoData = true)
        }
    }

    fun fechaCaixaData() {
        _uiState.update {
            it.copy(mostrarCaixaDialogoData = false)
        }
    }
}
