package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.R // Asegúrate de tener esta R

class PrincipalViewModel : ViewModel() {

    // Lista de juguetes (como en tu PDF)
    private val _juguetes = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetes: StateFlow<List<Juguete>> = _juguetes.asStateFlow()

    // --- LÓGICA AÑADIDA (Igual que en PassportViewModel) ---

    // Variable para guardar el juguete al que le diste clic
    private val _selectedJuguete = MutableStateFlow<Juguete?>(null)
    val selectedJuguete: StateFlow<Juguete?> = _selectedJuguete.asStateFlow()

    init {
        // Datos de ejemplo (como en tu PDF)
        _juguetes.value = listOf(
            Juguete(1, "Carrito", "Vehículos", 150, R.drawable.carrito),
            Juguete(2, "Muñeca", "Muñecas", 200, R.drawable.muneca),
            Juguete(3, "Lego", "Construcción", 300, R.drawable.lego)
            // NOTA: Debes tener "carrito.png", "muneca.png" y "lego.png" en tu carpeta res/drawable
        )
    }

    /**
     * Función que se llama al hacer clic en un juguete de la lista.
     * Guarda el juguete seleccionado en el StateFlow.
     */
    fun clickJuguete(juguete: Juguete) {
        _selectedJuguete.value = juguete
    }
}