package br.com.alura.helloapp.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import br.com.alura.helloapp.extensions.mostraMensagem
import br.com.alura.helloapp.ui.form.CadastroContatoActivity
import br.com.alura.helloapp.ui.form.TelaDetalhesContato
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import kotlinx.coroutines.launch

class DetalhesContatoActivity : ComponentActivity() {
    private val contatoDao by lazy {
        HelloAppDatabase.getDatabase(this).contatoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val idContato = carregaIdContato()

                    TelaDetalhesContato(viewModel = DetalhesContatoViewlModel(
                        contatoDao,
                        idContato
                    ),
                        onClickVoltar = { finish() },
                        onClickApagar = {
                            mostraMensagem(getString(R.string.contato_apagado))
                            finish()
                        },
                        onClickEditar = { vaiParaEditaContato(idContato) })
                }
            }
        }
    }

    private fun vaiParaEditaContato(idContato: Long) {
        val intent = Intent(this, CadastroContatoActivity::class.java)
        intent.putExtra(CHAVE_CONTATO_ID, idContato)
        startActivity(intent)
    }

    private fun carregaIdContato() = intent.getLongExtra(CHAVE_CONTATO_ID, 0L)
}
