package mx.edu.utez.prototipojugueteria.data.repository

import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.model.JugueteDao
import kotlinx.coroutines.flow.Flow

class JugueteRepository(private val dao: JugueteDao) { // Renombrado a 'dao'

    /** CREATE: inserta y devuelve el id autogenerado */
    suspend fun crear(juguete: Juguete): Long = dao.insert(juguete)

    /** READ: lista reactiva de todos los juguetes */
    fun listar(): Flow<List<Juguete>> = dao.getAll()

    /** READ: Devuelve un juguete por su id */
    fun getById(id: Long): Flow<Juguete> = dao.getById(id)

    /** UPDATE: actualiza un juguete */
    suspend fun actualizar(juguete: Juguete) = dao.update(juguete)

    /** DELETE: elimina un juguete */
    suspend fun eliminar(juguete: Juguete) = dao.delete(juguete)
}