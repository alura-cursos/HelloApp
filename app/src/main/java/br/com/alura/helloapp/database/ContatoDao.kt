package br.com.alura.helloapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.helloapp.data.Contato

@Dao
interface ContatoDao {

    @Insert
    suspend fun insere(contato: Contato)

    @Query("SELECT * FROM Contato")
    suspend fun buscaTodos(): List<Contato>
}