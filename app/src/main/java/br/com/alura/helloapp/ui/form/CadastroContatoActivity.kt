package br.com.alura.helloapp.ui.form

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.CHAVE_CONTATO_ID
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import kotlinx.coroutines.launch

class CadastroContatoActivity : ComponentActivity() {
    private val contatoDao by lazy {
        HelloAppDatabase.getDatabase(this).contatoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val idContato = carregaIdContato()

                    setContent {
                        TelaCadastro(
                            viewModel = CadastroContatoViewModel(contatoDao, idContato),
                            onClickSalvar = { contato ->
                                lifecycleScope.launch {
                                    contatoDao.insere(contato)
                                }
                                finish()
                            })
                    }

                }
            }
        }
    }

    private fun carregaIdContato() = intent.getLongExtra(CHAVE_CONTATO_ID, 0L)
}