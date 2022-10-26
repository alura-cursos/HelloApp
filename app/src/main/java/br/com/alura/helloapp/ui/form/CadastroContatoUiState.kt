package br.com.alura.helloapp.ui.form

import br.com.alura.helloapp.data.Contato

data class CadastroContatoUiState(
    val contato: Contato = Contato(),
    val mostrarCaixaDialogoImagem: Boolean = false,
    val mostrarCaixaDialogoData: Boolean = false,
    val onNomeMudou: (String) -> Unit = {},
    val onSobrenomeMudou: (String) -> Unit = {},
    val onTelefoneMudou: (String) -> Unit = {},
    val onFotoPerfilMudou: (String) -> Unit = {},
    val onAniversarioMudou: (String) -> Unit = {},
    var textoAniversairo: String = ""
) {
}
