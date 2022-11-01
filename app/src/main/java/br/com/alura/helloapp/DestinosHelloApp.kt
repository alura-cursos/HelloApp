package br.com.alura.helloapp

import androidx.navigation.NavType
import androidx.navigation.navArgument

object DestinoInicial {
    const val rota = ListaContatos.rota
}

object ListaContatos {
    const val rota = "lista_contatos"
}

object FormularioContato {
    const val rota = "cadastro_contato"
    const val idContato = "id_contato"
    const val rotaComArgumentos = "$rota/{$idContato}"
    val argumentos = listOf(
        navArgument(idContato) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object DetalhesContato {
    const val rota = "detalhes_contato"
    const val idContato = "id_contato"
    const val rotaComArgumentos = "$rota/{$idContato}"
    val argumentos = listOf(
        navArgument(idContato) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}