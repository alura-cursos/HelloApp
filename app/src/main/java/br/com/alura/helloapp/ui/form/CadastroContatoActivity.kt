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

        val idContato = intent.getLongExtra(CHAVE_CONTATO_ID, 0L)

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    var contatoCarregado by remember { mutableStateOf(Contato()) }

                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(coroutineScope) {
                        contatoDao.buscaPorId(idContato).collect {
                            it?.let { contatoCarregado = it }
                            setContent {
                                TelaCadastro(
                                    contatoCarregado = contatoCarregado,
                                    tituloAppBar = defineTituloAppBar(idContato),
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
        }
    }

    private fun defineTituloAppBar(idContato: Long): String {
        return if (idContato == 0L) getString(R.string.title_activity_cadastro_contato)
        else getString(R.string.title_activity_editar_contato)
    }
}