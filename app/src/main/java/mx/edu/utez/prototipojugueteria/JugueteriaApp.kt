package mx.edu.utez.prototipojugueteria

import android.app.Application
import androidx.room.Room
import mx.edu.utez.prototipojugueteria.data.model.VetDatabase
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

/**
 * Clase de Application para proveer la base de datos y el repositorio
 * a toda la aplicación (Inyección de Dependencias manual).
 */
class JugueteriaApp : Application() {

    // Crea la base de datos UNA SOLA VEZ (lazy)
    private val database: VetDatabase by lazy {
        Room.databaseBuilder(
            this,
            VetDatabase::class.java,
            "jugueteria_database" // Nombre del archivo de la base de datos
        ).build()
    }

    // Crea el repositorio UNA SOLA VEZ (lazy) usando el DAO de la base de datos
    val repository: JugueteRepository by lazy {
        JugueteRepository(database.jugueteDao()) // <-- Esto usa la función del archivo VetDatabase
    }
}