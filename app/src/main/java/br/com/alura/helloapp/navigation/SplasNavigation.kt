package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.ui.navegaDireto
import br.com.alura.helloapp.ui.splashscreen.SplashScreen
import br.com.alura.helloapp.ui.splashscreen.SplashScreenViewModel

fun NavGraphBuilder.splashGraph(
    navController: NavHostController
) {
    composable(
        route = DestinosHelloApp.SplashScreen.rota
    ) {
        SplashScreen()

        val viewModel = hiltViewModel<SplashScreenViewModel>()
        val state = viewModel.uiState.collectAsState()

        LaunchedEffect(state.value.carregando) {
            if (!state.value.carregando) {
                navController.popBackStack()
                navController.navegaDireto(state.value.destino_inicial)
            }
        }
    }
}