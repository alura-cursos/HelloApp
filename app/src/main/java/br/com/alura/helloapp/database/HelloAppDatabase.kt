package br.com.alura.helloapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.converter.Converters

@Database(entities = [Contato::class], version = 1)
@TypeConverters(Converters::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao

    companion object {
        fun getDatabase(context: Context): HelloAppDatabase {
            return Room.databaseBuilder(
                context,
                HelloAppDatabase::class.java,
                "heloApp.dp"
            ).build()
        }
    }
}