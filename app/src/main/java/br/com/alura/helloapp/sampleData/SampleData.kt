package br.com.alura.helloapp.sampleData

import br.com.alura.helloapp.data.Contato
import java.util.*

var contatosExemplo: List<Contato> = listOf(
    Contato(
        nome = "Ana",
        sobrenome = "Clara",
        telefone = "123",
        fotoPerfil = "https://images.pexels.com/photos/3922294/pexels-photo-3922294.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",

        ),
    Contato(
        nome = "Bill",
        sobrenome = "Lima",
        telefone = "321",
        fotoPerfil = "https://images.pexels.com/photos/1415882/pexels-photo-1415882.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        aniversario = Calendar.getInstance().time
    ),
    Contato(
        nome = "Odes",
        sobrenome = "Conhecido",
        telefone = "321",
        fotoPerfil = "urlTesteParaDarErro"
    )
)