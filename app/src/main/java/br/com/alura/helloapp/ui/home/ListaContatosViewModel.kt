package br.com.alura.helloapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.alura.helloapp.database.ContatoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            buscaProdutos()
        }
    }

    private suspend fun buscaProdutos() {
        contatoDao.buscaTodos().collect() {
            _uiState.value = _uiState.value.copy(
                contatos = it
            )
        }
    }
}


//@Suppress("UNCHECKED_CAST")
//class ListaContatosFactory @Inject constructor(private val contatoDao: ContatoDao) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(
//        modelClass: Class<T>,
//        extras: CreationExtras
//    ): T {
//        return ListaContatosViewModel(contatoDao) as T
//    }
//}