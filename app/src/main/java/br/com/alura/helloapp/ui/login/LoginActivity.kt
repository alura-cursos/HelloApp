package br.com.alura.helloapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.util.preferences.PreferencesKeys.NOME_USUARIO
import br.com.alura.helloapp.util.preferences.PreferencesKeys.USUARIO_LOGADO
import br.com.alura.helloapp.util.preferences.dataStore
import br.com.alura.helloapp.ui.home.ListaContatosActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verificaSeJaEstaLogado()

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    HomeLogin(onClickEntrar = {
                        startActivity(Intent(this, ListaContatosActivity::class.java))
                        this.finish()
                    }, onClickCriarLogin = { usuario ->
                        gravaLogin(usuario)
                    })

                }
            }
        }
    }

    private fun verificaSeJaEstaLogado() {
        // Esse código faz com que, deopis de logado, a tela apareça alguns instantes e depois mude para a MainActivity
        // Temos duas soluções:
        // 1- A que será gravada: Mover para a main e redirecionar pra cá se necessario.
        // 2- Implementar SplashScreen, deixar isso nas atividades

        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                if (preferences[USUARIO_LOGADO] == true) {
                    startActivity(Intent(this@LoginActivity, ListaContatosActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun gravaLogin(usuario: Usuario) {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[NOME_USUARIO] = usuario.nome
                preferences[USUARIO_LOGADO] = true
            }
        }
    }
}

@Composable
fun HomeLogin(onClickEntrar: () -> Unit, onClickCriarLogin: (usuario: Usuario) -> Unit) {
    var screenState: LoginScreens by remember {
        mutableStateOf(LoginScreens.Login)
    }

    when (screenState) {
        LoginScreens.Login -> LoginScreen(onClickEntrar = onClickEntrar) {
            screenState = LoginScreens.Cadastro
        }
        LoginScreens.Cadastro -> CadastroLoginScreen() {
            onClickCriarLogin(it)
            screenState = LoginScreens.Login
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeLoginPreview() {
    HomeLogin({}, {})
}


sealed class LoginScreens {
    object Login : LoginScreens()
    object Cadastro : LoginScreens()
}
