package br.com.alura.helloapp

import java.text.SimpleDateFormat
import java.util.*

fun Date.converteParaString(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
}
