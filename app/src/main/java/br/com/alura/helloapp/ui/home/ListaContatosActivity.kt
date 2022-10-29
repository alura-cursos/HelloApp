package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.R
import br.com.alura.helloapp.extensions.mostraMensagem
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.theme.HelloAppTheme

class ListaContatosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                HelloAppNavHost(
                    navController = navController,
                    onContatoApagado = {
                        mostraMensagem(getString(R.string.contato_apagado))
                    }
                )
            }
        }
    }
}
