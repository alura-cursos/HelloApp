package br.com.alura.helloapp

import java.text.SimpleDateFormat
import java.util.*

fun Date.converteParaString(): String {
    return SimpleDateFormat(FORMATO_DATA_DIA_MES_ANO, Locale.getDefault()).format(this)
}
