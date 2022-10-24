package br.com.alura.helloapp

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.converteParaDate(): Date? {
    return try {
        SimpleDateFormat(FORMATO_DATA_DIA_MES_ANO, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        null
    }
}