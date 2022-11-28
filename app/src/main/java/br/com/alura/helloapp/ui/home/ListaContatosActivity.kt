package br.com.alura.helloapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.sampleData.contatosExemplo
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                HelloAppNavHost(
                    navController = navController,
                )
            }
        }
    }
}