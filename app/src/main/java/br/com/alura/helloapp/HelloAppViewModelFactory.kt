package br.com.alura.helloapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel

@Suppress("UNCHECKED_CAST")
fun helloAppViewModelFactory(idContato: Long): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T =
            with(modelClass) {
                val application = checkNotNull(extras[APPLICATION_KEY]) as HelloAppAplication
                val contatoDao = application.database.contatoDao()
                when {
                    isAssignableFrom(FormularioContatoViewModel::class.java) ->
                        FormularioContatoViewModel(
                            contatoDao, idContato
                        )
                    isAssignableFrom(DetalhesContatoViewlModel::class.java) ->
                        DetalhesContatoViewlModel(
                            contatoDao, idContato
                        )
                    else -> throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
                }
            } as T
    }
}