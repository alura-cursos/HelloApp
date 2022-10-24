package br.com.alura.helloapp.ui.form

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
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import kotlinx.coroutines.launch

class DetalhesContatoActivity : ComponentActivity() {
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
                    var contatoCarregado by remember {
                        mutableStateOf(Contato())
                    }

                    val coroutineScope = rememberCoroutineScope()
                    LaunchedEffect(null) {
                        coroutineScope.launch {
                            contatoDao.buscaPorId(idContato).collect {
                                it?.let { contatoCarregado = it }
                            }
                        }
                    }

                    TelaDetalhesContato(
                        contato = contatoCarregado,
                        onClickVoltar = { finish() },
                        onClickApagar = {
                            lifecycleScope.launch {
                                contatoDao.remove(idContato)
                                Toast.makeText(
                                    this@DetalhesContatoActivity,
                                    getString(R.string.contato_apagado),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        },
                        onClickEditar = {
                            val intent = Intent(this, CadastroContatoActivity::class.java)
                            intent.putExtra(CHAVE_CONTATO_ID, idContato)
                            startActivity(intent)
                        })
                }
            }
        }
    }
}
