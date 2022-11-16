package br.com.alura.helloapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.helloapp.*
import br.com.alura.helloapp.navigation.*

@Composable
fun HelloAppNavHost(
    navController: NavHostController,
    destinoInicial: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = destinoInicial,
        modifier = modifier
    ) {
        homeGraph(navController)
        formularioContatoGraph(navController)
        detalhesContatoGraph(navController)
        loginGraph(navController)
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
    navegaDireto("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    navigate("${FormularioContato.rota}/$idContato")
}

fun NavHostController.navegaParaLoginDeslogado() {
    popBackStack(DestinosHelloApp.ListaContatos.rota, true)
    navegaDireto(DestinosHelloApp.LoginGraph.rota)
}

fun NavHostController.navegaParaListaPosLogin() {
    popBackStack(DestinosHelloApp.LoginGraph.rota, true)
    navegaDireto(DestinosHelloApp.HomeGraph.rota)
}
