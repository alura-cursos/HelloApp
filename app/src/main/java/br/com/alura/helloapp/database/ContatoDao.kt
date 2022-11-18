package br.com.alura.helloapp.database

import androidx.room.*
import br.com.alura.helloapp.data.Contato
import kotlinx.coroutines.flow.Flow

@Dao
interface ContatoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insere(contato: Contato)

    @Query("SELECT * FROM Contato")
    fun buscaTodos(): Flow<List<Contato>>

    @Query("SELECT * FROM Contato WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Contato?>

    @Query("DELETE FROM Contato WHERE id = :id")
    suspend fun remove(id: Long)
}