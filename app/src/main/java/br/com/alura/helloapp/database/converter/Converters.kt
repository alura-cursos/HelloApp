package br.com.alura.helloapp.database.converter

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun deLongParaDate(data: Long?): Date? {
        return data?.let { Date(it) }
    }

    @TypeConverter
    fun dateParaLong(data: Date?): Long? {
        return data?.time
    }
}