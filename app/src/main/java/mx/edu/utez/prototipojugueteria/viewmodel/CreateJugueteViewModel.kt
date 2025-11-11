package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class CreateJugueteViewModel(private val repository: JugueteRepository) : ViewModel() {
    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var precio by mutableStateOf("")

    fun createJuguete() {
        val precioDouble = precio.toDoubleOrNull()

        if (nombre.isNotBlank() && descripcion.isNotBlank() && precioDouble != null) {
            viewModelScope.launch {
                repository.crear(
                    Juguete(
                        nombre = nombre,
                        tipoJuguete = descripcion,
                        precio = precioDouble,
                        image = R.drawable.loginutez
                    )
                )
            }
        } else {
            // TODO: Mostrar error al usuario
        }
    }
}