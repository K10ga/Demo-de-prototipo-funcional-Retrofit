package mx.edu.utez.prototipojugueteria.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

/**
 * Esta es la Factory específica para JugueteViewModel,
 * basada en tu PetViewModelFactory.
 */
class JugueteViewModelFactory(
    private val repository: JugueteRepository,
    private val context: Context // <-- Pasamos el context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JugueteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JugueteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}