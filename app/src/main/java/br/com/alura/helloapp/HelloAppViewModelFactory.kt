package br.com.alura.helloapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel

@Suppress("UNCHECKED_CAST")
fun helloAppViewModelFactory(
    contatoDao: ContatoDao,
    idContato: Long = 0L
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T =
            with(modelClass) {
                when {
                    isAssignableFrom(FormularioContatoViewModel::class.java) ->
                        TODO()
                    isAssignableFrom(DetalhesContatoViewlModel::class.java) ->
                        DetalhesContatoViewlModel(
                            contatoDao,
                            idContato
                        )
                    else -> throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
                }
            } as T
    }
}