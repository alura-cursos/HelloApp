package br.com.alura.helloapp.di.module

import android.content.Context
import androidx.room.Room
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.database.HelloAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "helloApp.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideHelloAppDatabase(@ApplicationContext context: Context): HelloAppDatabase {
        return Room.databaseBuilder(
            context, HelloAppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideDao(db: HelloAppDatabase): ContatoDao {
        return db.contatoDao()
    }
}