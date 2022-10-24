package br.com.alura.helloapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.CHAVE_CONTATO_ID
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.ui.components.OnResumeCicloVidaAtual
import br.com.alura.helloapp.ui.form.CadastroContatoActivity
import br.com.alura.helloapp.ui.form.DetalhesContatoActivity
import br.com.alura.helloapp.ui.login.LoginActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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

                    var contatosBuscados by remember {
                        mutableStateOf(emptyList<Contato>())
                    }

                    val coroutineScope = rememberCoroutineScope()
                    OnResumeCicloVidaAtual(LocalLifecycleOwner.current) {
                        coroutineScope.launch {
                            contatoDao.buscaTodos().collect() {
                                contatosBuscados = it
                            }
                        }
                    }

                    TelaPrincipal(
                        contatosBuscados = contatosBuscados,
                        onClickDeslogar = {
                            lifecycleScope.launch {
                                dataStore.edit {
                                    it.remove(PreferencesKeys.USUARIO_LOGADO)
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            }
                        },
                        onClickAbreDetalhes = { contato ->
                            val intent = Intent(this, DetalhesContatoActivity::class.java)
                            intent.putExtra(CHAVE_CONTATO_ID, contato.id)
                            startActivity(intent)
                        }
                    ) {
                        val intent = Intent(this, CadastroContatoActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

