package br.com.alura.helloapp.ui.form

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.extensions.converteParaDate
import br.com.alura.helloapp.extensions.converteParaString
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormularioContatoViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idContato = savedStateHandle.get<Long>("id_contato")

    private val _uiState = MutableStateFlow(FormularioContatoUiState())
    val uiState: StateFlow<FormularioContatoUiState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            carregaContato()
        }

        _uiState.update { state ->
            state.copy(onNomeMudou = {
                _uiState.value = _uiState.value.copy(
                    nome = it
                )
            }, onSobrenomeMudou = {
                _uiState.value = _uiState.value.copy(
                    sobrenome = it
                )
            }, onTelefoneMudou = {
                _uiState.value = _uiState.value.copy(
                    telefone = it
                )
            }, onFotoPerfilMudou = {
                _uiState.value = _uiState.value.copy(
                    fotoPerfil = it
                )
            }, onAniversarioMudou = {
                _uiState.value = _uiState.value.copy(
                    aniversario = it.converteParaDate(), mostrarCaixaDialogoData = false
                )
            }, onMostrarCaixaDialogoImagem = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoImagem = it
                )
            }, onMostrarCaixaDialogoData = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoData = it
                )
            })
        }
    }

    suspend fun carregaContato() {
        idContato?.let {
            contatoDao.buscaPorId(idContato)?.let { contatoEncontrado ->
                with(contatoEncontrado) {
                    _uiState.value = _uiState.value.copy(
                        idContato = id,
                        nome = nome,
                        sobrenome = sobrenome,
                        telefone = telefone,
                        fotoPerfil = fotoPerfil,
                        aniversario = aniversario,
                        tituloAppbar = R.string.titulo_activity_editar_contato
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
                        id = idContato,
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

    fun defineTextoAniversario(textoAniversario: String) {
        val textoAniversairo = _uiState.value.aniversario?.converteParaString() ?: textoAniversario

        _uiState.update {
            it.copy(textoAniversairo = textoAniversairo)
        }
    }

    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = url, mostrarCaixaDialogoImagem = false
        )
    }

}
