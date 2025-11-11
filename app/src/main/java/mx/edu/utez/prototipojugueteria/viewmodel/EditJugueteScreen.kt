package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class EditJugueteViewModel(private val repository: JugueteRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    // Obtiene el ID del juguete desde los argumentos de navegación
    private val jugueteId: Long = checkNotNull(savedStateHandle["id"])

    // Expone el estado de la UI (JugueteUiState) basado en el ID
    val uiState: StateFlow<JugueteUiState> = repository.getById(jugueteId)
        .filterNotNull()
        .map {
            // Convierte el modelo Juguete a JugueteUiState
            JugueteUiState(it.id, it.nombre, it.tipoJuguete ?: "", it.precio.toString(), it.image)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = JugueteUiState()
        )

    // Actualiza el juguete en la base de datos
    fun update(jugueteUiState: JugueteUiState) {
        val precioDouble = jugueteUiState.precio.toDoubleOrNull()
        if (precioDouble != null) {
            viewModelScope.launch {
                repository.actualizar(jugueteUiState.toJuguete(precioDouble))
            }
        }
    }

    // Elimina el juguete
    fun delete() {
        viewModelScope.launch {
            repository.eliminar(uiState.value.toJuguete(uiState.value.precio.toDouble()))
        }
    }

    // Función helper para convertir JugueteUiState de nuevo a Juguete (modelo)
    private fun JugueteUiState.toJuguete(precio: Double): Juguete = Juguete(
        id = id,
        nombre = nombre,
        tipoJuguete = tipoJuguete,
        precio = precio,
        image = image
    )
}