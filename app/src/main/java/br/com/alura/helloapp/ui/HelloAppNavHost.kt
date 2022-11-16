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
    this.navegaDireto("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    this.navigate("${FormularioContato.rota}/$idContato")
}

fun NavHostController.navegaParaLoginDeslogado() {
    this.popBackStack(DestinosHelloApp.ListaContatos.rota, true)
    this.navegaDireto(DestinosHelloApp.LoginGraph.rota)
}

fun NavHostController.navegaParaListaPosLogin() {
    this.popBackStack(DestinosHelloApp.LoginGraph.rota, true)
    this.navegaDireto(DestinosHelloApp.HomeGraph.rota)
}
