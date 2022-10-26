package br.com.alura.helloapp.ui.home

import br.com.alura.helloapp.data.Contato

data class PrincipalUiState(
    val contatos: List<Contato> = emptyList()
)