package mx.edu.utez.prototipojugueteria.data

import android.content.Context
import mx.edu.utez.prototipojugueteria.data.model.JugueteDataBase
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

// CAMBIAR NOMBRE DE LA CLASE
class JugueteriaDataContainer(context: Context) {
    val jugueteRepository by lazy {
        JugueteRepository(JugueteDataBase.get(context).jugueteDao())
    }
}