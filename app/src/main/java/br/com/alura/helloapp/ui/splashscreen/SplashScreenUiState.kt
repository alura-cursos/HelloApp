package br.com.alura.helloapp.ui.splashscreen

import br.com.alura.helloapp.DestinoInicial

data class SplashScreenUiState(
    val carregando: Boolean = true,
    val destinoInicial: String = DestinoInicial.rota
)
