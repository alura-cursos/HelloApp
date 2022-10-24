package br.com.alura.helloapp.ui.form

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.CHAVE_CONTATO_ID
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.sampleData.contatosExemplo
import br.com.alura.helloapp.ui.components.OnResumeCicloVidaAtual
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                    OnResumeCicloVidaAtual(LocalLifecycleOwner.current) {
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
