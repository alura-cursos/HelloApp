package br.com.alura.helloapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.converter.Converters

@Database(
    entities = [Contato::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao

    companion object {
        @Volatile
        private var Instancia: HelloAppDatabase? = null

        fun getDatabase(context: Context): HelloAppDatabase {
            return Instancia ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    HelloAppDatabase::class.java,
                    "helloApp.db"
                ).build()
                Instancia = instancia
                instancia
            }
        }
    }
}