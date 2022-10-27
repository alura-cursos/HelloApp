package br.com.alura.helloapp.extensions

import android.content.Context
import android.widget.Toast

fun Context.mostraMensagem(texto: String) {
    Toast.makeText(
        this,
        texto,
        Toast.LENGTH_SHORT
    ).show()
}