package mx.edu.utez.prototipojugueteria.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JugueteDao{
    @Insert
    suspend fun insertJuguete(juguete: Juguete)

    @Query("SELECT * FROM juguetes ORDER BY nombreJuguete ASC")
    fun getAllJuguetes(): Flow<List<Juguete>>

    @Query("SELECT * FROM juguetes WHERE id = :jugueteId")
    suspend fun getJugueteById(jugueteId: Int): Juguete?
}