package mx.edu.utez.prototipojugueteria.viewmodel
/*
import androidx.lifecycle.ViewModel
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class JugueteViewModel(private val repository: JugueteRepository) : ViewModel(){
    val JugueteUiState: StateFlow<List<Juguete>> = repository.allJuguetes
    .stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addNewJuguete( nombreJuguete: String, tipoJuguete: String, precio: Float, imagen: Int){
        viewModelScope.launch {
            val newJuguete = Juguete(nombreJuguete = nombreJuguete, tipoJuguete = tipoJuguete, precio = precio, imagen = imagen )
       repository.insertJuguete(newJuguete)
        }
    }
}

 */