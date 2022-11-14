package br.com.alura.helloapp.ui

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.helloapp.*
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.ui.details.DetalhesContatoTela
import br.com.alura.helloapp.ui.form.FormularioContatoTela
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel
import br.com.alura.helloapp.ui.home.ListaContatosTela
import br.com.alura.helloapp.ui.home.ListaContatosViewModel
import br.com.alura.helloapp.ui.login.*
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore

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
            ListaContatosTela(
                viewModel = viewModel,
                onClickAbreDetalhes = { idContato ->
                    navController.navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                },
                onClickDesloga = {
                    navController.navegaParaLoginDeslogado()
                })

            val dataStore = LocalContext.current.dataStore

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
                DetalhesContato.idContato
            )?.let { idContato ->

                val viewModel = hiltViewModel<FormularioContatoViewModel>()

                FormularioContatoTela(
                    viewModel = viewModel,
                    onClickSalvar = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = DetalhesContato.rotaComArgumentos,
            arguments = DetalhesContato.argumentos
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong(
                DetalhesContato.idContato
            )?.let { idContato ->

                val contatoDao = HelloAppDatabase.getDatabase(LocalContext.current).contatoDao()

                DetalhesContatoTela(viewModel = viewModel(
                    factory = helloAppViewModelFactory(
                        contatoDao, idContato
                    )
                ), onClickVoltar = { navController.navigateUp() },
                    onClickApagar = {
                        navController.navigateUp()
                    }, onClickEditar = { navController.navegaParaFormularioContato(idContato) })
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
