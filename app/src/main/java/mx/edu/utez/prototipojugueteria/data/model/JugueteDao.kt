package mx.edu.utez.prototipojugueteria.data.model
/*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JugueteDao{

    @Insert
    suspend fun insert(juguete: Juguete): Long

    @Update
    suspend fun update(juguete: Juguete)

    @Delete
    suspend fun delete(juguete: Juguete)

    @Query("SELECT * FROM juguetes ORDER BY nombre ASC")
    fun getAll(): Flow<List<Juguete>>

    @Query("SELECT * FROM juguetes WHERE id = :id")
    fun getById(id: Long): Flow<Juguete>
}

 */