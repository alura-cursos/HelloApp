package br.com.alura.helloapp.ui.details

import android.app.Application
import android.text.Spannable.Factory
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.alura.helloapp.HelloAppAplication
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.database.HelloAppDatabase
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
                _uiState.value = _uiState.value.copy(
                    contato = contatoEncontrado
                )
            }
        }
    }

    fun removeContato() {
        viewModelScope.launch {
            contatoDao.remove(idContato)
        }
    }
}


@Suppress("UNCHECKED_CAST")
class DetalhesContatoFactory(private val idContato: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val appAplication = checkNotNull(extras[APPLICATION_KEY])
        return DetalhesContatoViewlModel(
            (appAplication as HelloAppAplication).database.contatoDao(),
            idContato
        ) as T
    }
}

