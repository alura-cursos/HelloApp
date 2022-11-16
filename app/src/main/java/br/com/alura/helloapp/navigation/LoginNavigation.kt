package br.com.alura.helloapp.navigation

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
import br.com.alura.helloapp.ui.navegaDireto
import br.com.alura.helloapp.ui.navegaParaListaPosLogin
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
            val scope = rememberCoroutineScope()

            LoginTela(
                state = state,
                onClickLogar = {
                    scope.launch {
                        viewModel.logar()
                    }
                    if (state.logado) {
                        navController.navegaParaListaPosLogin()
                    }
                },
                onClickCriarLogin = {
                    navController.navegaDireto(DestinosHelloApp.FormularioLogin.rota)
                }
            )
        }

        composable(
            route = DestinosHelloApp.FormularioLogin.rota,
        ) {
            val viewModel = hiltViewModel<FormularioLoginViewModel>()
            val state by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()

            FormularioLoginTela(
                state = state,
                onSalvar = {
                    scope.launch {
                        viewModel.salvarLogin()
                    }
                    navController.navegaDireto(DestinosHelloApp.LoginGraph.rota)
                }
            )
        }
    }
}