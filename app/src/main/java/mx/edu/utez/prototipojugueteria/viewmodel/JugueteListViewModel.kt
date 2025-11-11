package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.R // Asegúrate de tener esta imagen de placeholder
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class JugueteListViewModel(private val repository: JugueteRepository) : ViewModel() {

    val items: StateFlow<List<Juguete>> = repository.listar()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    // Opcional: Código para agregar datos de ejemplo si la lista está vacía
    init {
        viewModelScope.launch {
            if (repository.listar().first().isEmpty()) {
                repository.crear(Juguete(nombre = "Carrito", tipoJuguete = "Juguete de madera", precio = 150.0, image = R.drawable.carrito))
                repository.crear(Juguete(nombre = "Muñeca", tipoJuguete = "Juguete de trapo", precio = 200.0, image = R.drawable.carrito))
            }
        }
    }
}