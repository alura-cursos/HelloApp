package br.com.alura.helloapp

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface DestinosHelloApp {
    val rota: String

}

object ListaContatos : DestinosHelloApp {
    override val rota = "listaContatos"

}

object CadastroContato : DestinosHelloApp {
    override val rota = "cadastro_contato"
    const val idContato = "id_contato"
    val rotaComArgumentos = "${CadastroContato.rota}/{$idContato}"
    val argumentos = listOf(
        navArgument(idContato) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object DetalhesContato : DestinosHelloApp {
    override val rota = "detalhes_contato"
    const val idContato = "id_contato"
    val rotaComArgumentos = "$rota/{$idContato}"
    val argumentos = listOf(
        navArgument(idContato) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object EditaContato : DestinosHelloApp {
    override val rota = "edita_contato"
    const val idContato = "id_contato"
}

val telasHelloApp = listOf(
    ListaContatos,
    CadastroContato,
    DetalhesContato,
    EditaContato
)