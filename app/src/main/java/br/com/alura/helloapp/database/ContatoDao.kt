package br.com.alura.helloapp.database

import androidx.room.*
import br.com.alura.helloapp.data.Contato


@Dao
interface ContatoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insere(contato: Contato)

    @Query("SELECT * FROM Contato")
    fun buscaTodos(): List<Contato>
}