package br.com.alura.helloapp.extensions

import android.content.Context
import android.widget.Toast
import br.com.alura.helloapp.R

fun Context.mostraMensagem(texto: String) {
    Toast.makeText(
        this, texto, Toast.LENGTH_SHORT
    ).show()
}