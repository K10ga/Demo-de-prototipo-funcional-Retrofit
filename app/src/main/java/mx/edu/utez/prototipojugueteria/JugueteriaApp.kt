package mx.edu.utez.prototipojugueteria

import android.app.Application
// Importa la clase renombrada
import mx.edu.utez.prototipojugueteria.data.JugueteriaDataContainer

// Esta clase Application gestiona la instancia del contenedor.
class JugueteriaApp : Application() {

    // Usamos el tipo "JugueteriaDataContainer"
    lateinit var container: JugueteriaDataContainer

    override fun onCreate() {
        super.onCreate()
        // Inicializa el contenedor pasándole el contexto de la aplicación
        container = JugueteriaDataContainer(this)
    }
}