package br.com.alura.helloapp.di.module

import android.content.Context
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.database.HelloAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideContatoDao(@ApplicationContext context: Context): ContatoDao {
        return HelloAppDatabase.getDatabase(context).contatoDao()
    }
}