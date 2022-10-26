package br.com.alura.helloapp.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.helloapp.CadastroContato
import br.com.alura.helloapp.DetalhesContato
import br.com.alura.helloapp.ListaContatos
import br.com.alura.helloapp.R
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.extensions.mostraMensagem
import br.com.alura.helloapp.ui.details.DetalhesContatoViewlModel
import br.com.alura.helloapp.ui.form.CadastroContatoTela
import br.com.alura.helloapp.ui.form.CadastroContatoViewModel
import br.com.alura.helloapp.ui.form.DetalhesContatoTela
import br.com.alura.helloapp.ui.home.ListaContatosTela
import br.com.alura.helloapp.ui.home.ListaContatosViewModel

@Composable
fun HelloAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    contatoDao: ContatoDao,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = ListaContatos.rota,
        modifier = modifier
    ) {
        composable(route = ListaContatos.rota) {
            ListaContatosTela(
                viewModel = ListaContatosViewModel(contatoDao),
                onClickDeslogar = {},
                onClickAbreDetalhes = {
                    navController
                        .navegaParaDetalhes(it.id)
                },
                onClickAbreCadastro = {
                    navController.navegaCadastro()
                })
        }

        composable(
            route = CadastroContato.rotaComArgumentos,
            arguments = CadastroContato.argumentos
        ) { navBackStackEntry ->

            val idContato: Long =
                navBackStackEntry.arguments?.getLong(DetalhesContato.idContato, 0L)!!

            CadastroContatoTela(
                viewModel = CadastroContatoViewModel(
                    contatoDao, idContato
                ),
                onClickSalvar = {
                    navController.navigateUp()
                })
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
                    context.mostraMensagem(context.getString(R.string.contato_apagado))
                    navController.navigateUp()
                },
                onClickEditar = { navController.navegaCadastro(idContato) })
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

fun NavHostController.navegaCadastro(idContato: Long = 0L) {
    this.navigate("${CadastroContato.rota}/$idContato")
}
