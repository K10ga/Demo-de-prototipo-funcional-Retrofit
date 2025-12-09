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

    // --- NUEVO ESTADO: Alerta de datos desactualizados ---
    private val _dataChangedAlert = MutableStateFlow(false)
    val dataChangedAlert = _dataChangedAlert.asStateFlow()

    init {
        fetchJuguetes()
    }

    fun fetchJuguetes() {
        viewModelScope.launch {
            _juguetesUiState.value = repository.getJuguetes()
        }
    }

    fun addNewJuguete(nombre: String, tipo: String?, precio: Double, imageUris: List<Uri>) {
        viewModelScope.launch {
            val currentUserId = UserSession.currentUserId
            repository.insertJuguete(nombre, tipo, precio, imageUris, currentUserId)
        }
    }

    fun updateJuguete(id: Int, nombre: String, tipo: String?, precio: Double, imageUris: List<Uri>) {
        viewModelScope.launch {
            repository.updateJuguete(id, nombre, tipo, precio, imageUris)
        }
    }

    fun deleteJuguete(id: Int) {
        viewModelScope.launch { repository.deleteJuguete(id) }
    }

    // --- LÓGICA INTELIGENTE DE COMPRA ---
    fun attemptPurchase(jugueteId: Int, precioVisto: Double, nombreVisto: String, tipoVisto: String) {
        val compradorId = UserSession.currentUserId ?: return

        viewModelScope.launch {
            // 1. Obtener la versión MÁS RECIENTE del servidor
            val freshData = repository.getJugueteById(jugueteId)

            if (freshData != null) {
                // 2. Comprobar si hubo cambios críticos
                val haCambiado = (freshData.precio != precioVisto) ||
                        (freshData.nombre != nombreVisto) ||
                        (freshData.tipoJuguete != tipoVisto) ||
                        (freshData.vendido) // También falla si alguien más lo ganó antes

                if (haCambiado) {
                    // 3. Si cambió, activamos la alerta y actualizamos la vista
                    _dataChangedAlert.value = true
                    _selectedJuguete.value = freshData // Actualizamos lo que ve el usuario
                } else {
                    // 4. Si todo es igual, procedemos a comprar
                    repository.comprarJuguete(jugueteId, compradorId)
                    loadJuguete(jugueteId) // Refrescar para ver el "VENDIDO"
                }
            }
        }
    }

    // Función para cerrar la alerta
    fun dismissDataChangedAlert() {
        _dataChangedAlert.value = false
    }

    fun loadJuguete(id: Int) {
        viewModelScope.launch {
            _selectedJuguete.value = repository.getJugueteById(id)
        }
    }

    fun clearSelectedJuguete() {
        _selectedJuguete.value = null
        _dataChangedAlert.value = false // Limpiar alertas previas
    }
}