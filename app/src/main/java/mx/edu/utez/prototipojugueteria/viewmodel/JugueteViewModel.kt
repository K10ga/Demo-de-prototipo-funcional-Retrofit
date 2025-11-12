package mx.edu.utez.prototipojugueteria.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

/**
 * Este es el ÚNICO ViewModel para Juguetes,
 * basado en tu PetViewModel.
 */
class JugueteViewModel(private val repository: JugueteRepository) : ViewModel() {

    // --- 1. Lógica para la LISTA ---
    private val _juguetesUiState = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetesUiState = _juguetesUiState.asStateFlow() // La UI de lista observa esto

    init {
        fetchJuguetes() // Cargar la lista al iniciar
    }

    /**
     * Obtiene la lista de juguetes desde el repositorio (API)
     */
    fun fetchJuguetes() {
        viewModelScope.launch {
            _juguetesUiState.value = repository.getJuguetes()
        }
    }

    // --- 2. Lógica para AÑADIR ---

    /**
     * Añade un nuevo juguete y luego refresca la lista.
     */
    fun addNewJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            repository.insertJuguete(nombre, tipoJuguete, precio, imageUri)
            fetchJuguetes() // ¡Refresca la lista automáticamente!
        }
    }
}