package mx.edu.utez.prototipojugueteria.viewmodel
/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository
import java.lang.IllegalArgumentException

/**
 * Factory para crear instancias de JugueteViewModel con un JugueteRepository.
 */
class JugueteViewModelFactory(
    private val repository: JugueteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Revisa si la clase que se pide crear es JugueteViewModel
        if (modelClass.isAssignableFrom(JugueteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Si lo es, la crea y le pasa el repositorio
            return JugueteViewModel(repository) as T
        }
        // Si no, lanza una excepción
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

 */