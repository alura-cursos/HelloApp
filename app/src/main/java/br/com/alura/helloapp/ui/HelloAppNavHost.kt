package br.com.alura.helloapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.helloapp.FormularioContato
import br.com.alura.helloapp.DetalhesContato
import br.com.alura.helloapp.ListaContatos
import br.com.alura.helloapp.DestinoInicial
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.ui.details.DetalhesContatoTela
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.FormularioContatoTela
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel
import br.com.alura.helloapp.ui.home.ListaContatosTela
import br.com.alura.helloapp.ui.home.ListaContatosViewModel

@Composable
fun HelloAppNavHost(
    navController: NavHostController,
    contatoDao: ContatoDao,
    modifier: Modifier = Modifier,
    onContatoApagado: () -> Unit = {}

) {
    NavHost(
        navController = navController,
        startDestination = DestinoInicial.rota,
        modifier = modifier
    ) {
        composable(route = ListaContatos.rota) {
            ListaContatosTela(
                viewModel = ListaContatosViewModel(contatoDao),
                onClickDeslogar = {},
                onClickAbreDetalhes = { idContato ->
                    navController
                        .navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                })
        }

        composable(
            route = FormularioContato.rotaComArgumentos,
            arguments = FormularioContato.argumentos
        ) { navBackStackEntry ->

            navBackStackEntry.arguments?.getLong(
                DetalhesContato.idContato, 0L
            )?.let { idContato ->
                FormularioContatoTela(
                    viewModel = FormularioContatoViewModel(
                        contatoDao, idContato
                    ),
                    onClickSalvar = {
                        navController.navigateUp()
                    })
            }
        }

        composable(
            route = DetalhesContato.rotaComArgumentos,
            arguments = DetalhesContato.argumentos
        ) { navBackStackEntry ->
            val idContato: Long =
                navBackStackEntry.arguments?.getLong(DetalhesContato.idContato, 0L)!!
            DetalhesContatoTela(
                viewModel = DetalhesContatoViewlModel(contatoDao, idContato),
                onClickVoltar = { navController.navigateUp() },
                onClickApagar = {
                    onContatoApagado()
                    navController.navigateUp()
                },
                onClickEditar = { navController.navegaParaFormularioContato(idContato) })
        }
    }
}


fun NavHostController.navegaDiretoAoTopo(route: String) =
    this.navigate(route) {
        popUpTo(this@navegaDiretoAoTopo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navegaParaDetalhes(idContato: Long) {
    this.navegaDiretoAoTopo("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    this.navigate("${FormularioContato.rota}/$idContato")
}
