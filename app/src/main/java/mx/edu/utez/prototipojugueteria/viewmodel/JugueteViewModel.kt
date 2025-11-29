package mx.edu.utez.prototipojugueteria.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository
import mx.edu.utez.prototipojugueteria.utils.UserSession

class JugueteViewModel(private val repository: JugueteRepository) : ViewModel() {

    private val _juguetesUiState = MutableStateFlow<List<Juguete>>(emptyList())
    val juguetesUiState = _juguetesUiState.asStateFlow()

    private val _selectedJuguete = MutableStateFlow<Juguete?>(null)
    val selectedJuguete = _selectedJuguete.asStateFlow()

    init {
        fetchJuguetes()
    }

    fun fetchJuguetes() {
        viewModelScope.launch {
            _juguetesUiState.value = repository.getJuguetes()
        }
    }

    // --- AHORA RECIBE LISTA ---
    fun addNewJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUris: List<Uri> // Cambiado a List<Uri>
    ) {
        viewModelScope.launch {
            val currentUserId = UserSession.currentUserId
            repository.insertJuguete(nombre, tipoJuguete, precio, imageUris, currentUserId)
        }
    }

    fun updateJuguete(
        id: Int,
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUris: List<Uri> // Cambiado a List<Uri>
    ) {
        viewModelScope.launch {
            repository.updateJuguete(id, nombre, tipoJuguete, precio, imageUris)
        }
    }

    fun deleteJuguete(id: Int) {
        viewModelScope.launch {
            repository.deleteJuguete(id)
        }
    }

    // --- NUEVO: COMPRAR ---
    fun comprarJuguete(jugueteId: Int) {
        val compradorId = UserSession.currentUserId ?: return
        viewModelScope.launch {
            repository.comprarJuguete(jugueteId, compradorId)
            // Recargamos el juguete para ver que ya se vendió
            loadJuguete(jugueteId)
        }
    }

    fun loadJuguete(id: Int) {
        viewModelScope.launch {
            _selectedJuguete.value = repository.getJugueteById(id)
        }
    }

    fun clearSelectedJuguete() {
        _selectedJuguete.value = null
    }
}