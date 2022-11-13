package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {

//    private val contatoDao by lazy {
//        HelloAppDatabase.getDatabase(this).contatoDao()
//    }

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