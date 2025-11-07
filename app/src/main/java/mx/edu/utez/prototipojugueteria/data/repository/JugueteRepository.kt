package mx.edu.utez.prototipojugueteria.data.repository

import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.model.JugueteDao

class JugueteRepository(private val JugueteDao: JugueteDao) {

    //Expone el flow reactivo desde el DAO
    val allJuguetes: Flow<List<Juguete>> = JugueteDao.getAllJuguetes()

    //Maneja las operaciones de escritura (suspension)
    suspend fun insertJuguete(Juguete: Juguete){
        JugueteDao.insertJuguete(Juguete)
    }
}