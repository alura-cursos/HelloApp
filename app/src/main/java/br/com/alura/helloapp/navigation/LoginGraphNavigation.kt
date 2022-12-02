package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.ui.login.FormularioLoginTela
import br.com.alura.helloapp.ui.login.FormularioLoginViewModel
import br.com.alura.helloapp.ui.login.LoginTela
import br.com.alura.helloapp.ui.login.LoginViewModel
import br.com.alura.helloapp.ui.navegaLimpo
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = DestinosHelloApp.Login.rota,
        route = DestinosHelloApp.LoginGraph.rota
    ) {
        composable(
            route = DestinosHelloApp.Login.rota,
        ) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            if (state.logado) {
                LaunchedEffect(Unit) {
                    navController.navegaLimpo(DestinosHelloApp.HomeGraph.rota)
                }
            }

            val coroutineScope = rememberCoroutineScope()

            LoginTela(
                state = state,
                onClickLogar = {
                    coroutineScope.launch {
                        viewModel.tentaLogar()
                    }
                },
                onClickCriarLogin = {
                    navController.navigate(DestinosHelloApp.FormularioLogin.rota)
                }
            )

        }

        composable(
            route = DestinosHelloApp.FormularioLogin.rota,
        ) {
            val viewModel = hiltViewModel<FormularioLoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            val coroutineScope = rememberCoroutineScope()

            FormularioLoginTela(
                state = state,
                onSalvar = {
                    coroutineScope.launch {
                        viewModel.salvarLogin()
                    }
                    navController.navegaLimpo(DestinosHelloApp.Login.rota)
                }
            )
        }
    }
}