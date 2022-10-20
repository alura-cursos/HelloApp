package br.com.alura.helloapp.data

import java.util.*

data class Contato(
    val id: String,
    val nome: String,
    val sobreNome: String,
    val telefone: String,
    val fotoPerfil: String,
    val aniversario: Date? = null,
)