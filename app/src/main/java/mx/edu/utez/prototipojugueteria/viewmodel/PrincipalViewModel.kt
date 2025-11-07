package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.R
/*
class PrincipalViewModel : ViewModel() {

    private val _juguetes = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetes: StateFlow<List<Juguete>> = _juguetes.asStateFlow()


    private val _selectedJuguete = MutableStateFlow<Juguete?>(null)
    val selectedJuguete: StateFlow<Juguete?> = _selectedJuguete.asStateFlow()

    init {

        _juguetes.value = listOf(
            Juguete(1, "Carrito", "Vehículos", 150, R.drawable.carrito),
            Juguete(2, "Muñeca", "Muñecas", 200, R.drawable.muneca),
            Juguete(3, "Lego", "Construcción", 300, R.drawable.lego)

        )
    }


    fun clickJuguete(juguete: Juguete) {
        _selectedJuguete.value = juguete
    }
}
*/