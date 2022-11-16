package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.splashscreen.SplashScreenViewModel
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {

    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gerenciaSplashScreen()

        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                val destinoInicial =
                    splashScreenViewModel.uiState.collectAsState().value.destinoInicial

                HelloAppNavHost(
                    navController = navController,
                    destinoInicial = destinoInicial
                )
            }
        }
    }

    private fun gerenciaSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.uiState.value.carregando
        }
        splashScreen.setOnExitAnimationListener {
            it.remove()
        }
    }
}