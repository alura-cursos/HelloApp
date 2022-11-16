package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.splashscreen.SplashScreenViewModel
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gerenciaSplashScreen()

        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                val destinoInicial =
                    splashScreenViewModel.uiState.collectAsState().value.destino_inicial

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