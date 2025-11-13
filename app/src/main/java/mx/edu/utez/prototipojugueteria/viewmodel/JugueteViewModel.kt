package mx.edu.utez.prototipojugueteria.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class JugueteViewModel(private val repository: JugueteRepository) : ViewModel() {

    // --- 1. Estado para la LISTA de juguetes ---
    private val _juguetesUiState = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetesUiState = _juguetesUiState.asStateFlow()

    // --- 2. Estado para el juguete SELECCIONADO (para editar) ---
    private val _selectedJuguete = MutableStateFlow<Juguete?>(null)
    val selectedJuguete = _selectedJuguete.asStateFlow()

    init {
        // Cargar la lista inicial al crear el ViewModel
        fetchJuguetes()
    }

    /**
     * Esta es AHORA la ÚNICA función que actualiza la lista _juguetesUiState
     */
    fun fetchJuguetes() {
        viewModelScope.launch {
            _juguetesUiState.value = repository.getJuguetes()
        }
    }

    /**
     * Añade un juguete.
     * Nota: Ya NO actualiza el estado local.
     * El refresco se hará desde Navigation.kt
     */
    fun addNewJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            repository.insertJuguete(nombre, tipoJuguete, precio, imageUri)
            // ¡Ya no hay 'fetchJuguetes()' ni '_juguetesUiState.update' aquí!
        }
    }

    /**
     * Actualiza un juguete.
     * Nota: Ya NO actualiza el estado local.
     */
    fun updateJuguete(
        id: Int,
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            repository.updateJuguete(id, nombre, tipoJuguete, precio, imageUri)
        }
    }

    /**
     * Elimina un juguete.
     * Nota: Ya NO actualiza el estado local.
     */
    fun deleteJuguete(id: Int) {
        viewModelScope.launch {
            repository.deleteJuguete(id)
        }
    }

    // --- Lógica de carga/limpieza (sin cambios) ---

    /**
     * Carga un juguete específico por su ID en el estado _selectedJuguete.
     * Esto es para la pantalla de edición.
     */
    fun loadJuguete(id: Int) {
        viewModelScope.launch {
            _selectedJuguete.value = repository.getJugueteById(id)
        }
    }

    /**
     * Limpia el juguete seleccionado (se usa al salir de la pantalla de edición).
     */
    fun clearSelectedJuguete() {
        _selectedJuguete.value = null
    }
}