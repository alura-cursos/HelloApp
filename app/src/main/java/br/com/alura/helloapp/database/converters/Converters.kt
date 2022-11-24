package br.com.alura.helloapp.database.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun deDateParaLong(valor: Date?): Long?{
        return valor?.time
    }

    @TypeConverter
    fun deLongParaDate(valor: Long?): Date?{
        return valor?.let { Date(it) }
    }
}