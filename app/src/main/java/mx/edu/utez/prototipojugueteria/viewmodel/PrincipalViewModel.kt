package mx.edu.utez.prototipojugueteria.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.R

class PrincipalViewModel : ViewModel() {

    private val _juguetes = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetes: StateFlow<List<Juguete>> = _juguetes

    init {
        _juguetes.value = listOf(
            Juguete(1, "Carrito", "Vehículos", 150, R.drawable.carrito),
            Juguete(2, "Muñeca", "Muñecas", 200, R.drawable.muneca),
            Juguete(3, "Lego", "Construcción", 500, R.drawable.lego),
            Juguete(4, "Pelota", "Deportes", 120, R.drawable.pelota),
            Juguete(5, "Rompecabezas", "Educativo", 180, R.drawable.rompecabezas),
            Juguete(6, "Camión", "Vehículos", 250, R.drawable.camion)
        )
    }

    fun clickJuguete(juguete: Juguete) {
        println("Has hecho click en: ${juguete.nombreJuguete}")
    }
}