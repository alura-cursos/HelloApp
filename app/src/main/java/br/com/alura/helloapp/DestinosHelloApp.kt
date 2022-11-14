package br.com.alura.helloapp

import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.alura.helloapp.util.ID_CONTATO

object DestinoInicial {
    const val rota = ListaContatos.rota
}

object ListaContatos {
    const val rota = "lista_contatos"
}

object FormularioContato {
    const val rota = "formulario_contato"
    const val rotaComArgumentos = "$rota/{$ID_CONTATO}"
    val argumentos = listOf(
        navArgument(ID_CONTATO) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object DetalhesContato {
    const val rota = "detalhes_contato"
    const val rotaComArgumentos = "$rota/{$ID_CONTATO}"
    val argumentos = listOf(
        navArgument(ID_CONTATO) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object FormularioLogin {
    const val rota = "formulario_login"
}

object Login {
    const val rota = "login"
}