package br.com.alura.helloapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.*
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.details.DetalhesContatoActivity
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.CadastroContatoActivity
import br.com.alura.helloapp.ui.form.CadastroContatoTela
import br.com.alura.helloapp.ui.form.CadastroContatoViewModel
import br.com.alura.helloapp.ui.form.DetalhesContatoTela
import br.com.alura.helloapp.ui.login.LoginActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import kotlinx.coroutines.launch

class ListaContatosActivity : ComponentActivity() {
    private val contatoDao by lazy {
        HelloAppDatabase.getDatabase(this).contatoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAppTheme {
                val navController = rememberNavController()

                val backStackAtual by navController.currentBackStackEntryAsState()
                val destinoAtual = backStackAtual?.destination

                val telaAtual =
                    telasHelloApp.find { it.rota == destinoAtual?.route } ?: ListaContatos

                HelloAppNavHost(
                    navController = navController,
                    contatoDao = contatoDao,
                    context = this
                )

//                ListaContatosTela(
//                    viewModel = ListaContatosViewModel(contatoDao),
//                    onClickDeslogar = {
//                        lifecycleScope.launch {
//                            dataStore.edit {
//                                it.remove(PreferencesKeys.USUARIO_LOGADO)
//                                startActivity(
//                                    Intent(
//                                        this@ListaContatosActivity,
//                                        LoginActivity::class.java
//                                    )
//                                )
//                                finish()
//                            }
//                        }
//                    },
//                    onClickAbreDetalhes = { contato ->
//                        val intent = Intent(this, DetalhesContatoActivity::class.java)
//                        intent.putExtra(CHAVE_CONTATO_ID, contato.id)
//                        startActivity(intent)
//                    }
//                ) {
//                    val intent = Intent(this, CadastroContatoActivity::class.java)
//                    startActivity(intent)
//                }

            }
        }
    }


}
