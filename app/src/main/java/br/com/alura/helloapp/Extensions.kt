package br.com.alura.helloapp

import java.text.SimpleDateFormat
import java.util.*


fun Date.converteParaString(): String {
    val dataFormatadastring =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)

    return dataFormatadastring
}