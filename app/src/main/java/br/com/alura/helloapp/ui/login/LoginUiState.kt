package br.com.alura.helloapp.ui.login

data class LoginUiState(
    val usuario: String = "",
    val senha: String = "",
    val exibirErro: Boolean = false,
    val onErro: (Boolean) -> Unit = {},
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickLogar: () -> Unit = {},
    val logado: Boolean = false
)