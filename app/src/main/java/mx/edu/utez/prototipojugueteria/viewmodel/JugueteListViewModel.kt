package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class JugueteListViewModel(private val repository: JugueteRepository) : ViewModel() {

    // Usamos MutableStateFlow para actualizar la lista desde la API
    private val _juguetesUiState = MutableStateFlow<List<Juguete>>(emptyList())
    val items = _juguetesUiState.asStateFlow() // La UI observa 'items'

    init {
        fetchJuguetes() // Cargar la lista al iniciar
    }

    // Función para obtener los juguetes de la API
    fun fetchJuguetes() {
        viewModelScope.launch {
            _juguetesUiState.value = repository.getJuguetes()
        }
    }
}