package br.com.alura.helloapp.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.helloapp.*
import br.com.alura.helloapp.R
import br.com.alura.helloapp.extensions.mostraMensagem
import br.com.alura.helloapp.ui.details.DetalhesContatoTela
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.FormularioContatoTela
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel
import br.com.alura.helloapp.ui.home.ListaContatosTela
import br.com.alura.helloapp.ui.home.ListaContatosViewModel
import br.com.alura.helloapp.ui.login.FormularioLoginFactory
import br.com.alura.helloapp.ui.login.FormularioLoginTela
import br.com.alura.helloapp.ui.login.LoginFactory
import br.com.alura.helloapp.ui.login.LoginTela
import br.com.alura.helloapp.util.ID_CONTATO
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import kotlinx.coroutines.launch

@Composable
fun HelloAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DestinoInicial.rota,
        modifier = modifier
    ) {
        composable(route = ListaContatos.rota) {
            val viewModel = hiltViewModel<ListaContatosViewModel>()
            val state by viewModel.uiState.collectAsState()

            val dataStore = LocalContext.current.dataStore
            val scope = rememberCoroutineScope()

            ListaContatosTela(
                state = state,
                onClickAbreDetalhes = { idContato ->
                    navController.navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                },
                onClickDesloga = {
                    scope.launch {
                        dataStore.edit { preferences ->
                            preferences[PreferencesKeys.LOGADO] = false
                        }
                        navController.navegaParaLoginDeslogado()
                    }
                })


            LaunchedEffect(null) {
                dataStore.data.collect { preferences ->
                    if (preferences[PreferencesKeys.LOGADO] == false) {
                        navController.navegaParaLoginDeslogado()
                    }
                }
            }
        }

        composable(
            route = FormularioContato.rotaComArgumentos,
            arguments = FormularioContato.argumentos
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong(
                ID_CONTATO
            )?.let { idContato ->

                val viewModel = hiltViewModel<FormularioContatoViewModel>()
                val state by viewModel.uiState.collectAsState()

                viewModel.defineTextoAniversario(
                    stringResource(id = R.string.aniversario)
                )

                FormularioContatoTela(
                    state = state,
                    onClickSalvar = {
                        viewModel.salvaContato()
                        navController.popBackStack()
                    },
                    onCarregarImagem = {
                        viewModel.carregaImagem(it)
                    }
                )
            }
        }

        composable(
            route = DetalhesContato.rotaComArgumentos,
            arguments = DetalhesContato.argumentos
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong(
                ID_CONTATO
            )?.let { idContato ->

                val viewModel = hiltViewModel<DetalhesContatoViewlModel>()
                val state by viewModel.uiState.collectAsState()

                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current

                DetalhesContatoTela(
                    state = state,
                    onClickVoltar = { navController.navigateUp() },
                    onApagaContato = {
                        coroutineScope.launch {
                            viewModel.removeContato()
                            context.mostraMensagem(context.getString(R.string.contato_apagado))
                        }
                        navController.navigateUp()
                    },
                    onClickEditar = { navController.navegaParaFormularioContato(idContato) })
            }
        }

        composable(
            route = Login.rota,
        ) {
            LoginTela(
                viewModel = viewModel(factory = LoginFactory()),
                onClickLogar = {
                    navController.navegaParaListaPosLogin()
                },
                onClickCriarLogin = {
                    navController.navegaDireto(FormularioLogin.rota)
                })
        }

        composable(
            route = FormularioLogin.rota,
        ) { navBackStackEntry ->
            FormularioLoginTela(
                viewModel = viewModel(factory = FormularioLoginFactory()),
                onClickSalvar = {
                    navController.navegaDireto(Login.rota)
                })
        }
    }
}


fun NavHostController.navegaDireto(route: String) = this.navigate(route) {
    popUpTo(this@navegaDireto.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navegaParaDetalhes(idContato: Long) {
    this.navegaDireto("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    this.navigate("${FormularioContato.rota}/$idContato")
}

fun NavHostController.navegaParaLoginDeslogado() {
    this.popBackStack(ListaContatos.rota, true)
    this.navegaDireto(Login.rota)
}

fun NavHostController.navegaParaListaPosLogin() {
    this.popBackStack(Login.rota, true)
    this.navegaDireto(ListaContatos.rota)
}
