package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

class ViewModelFactory(private val repository: JugueteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        // Sabe cómo crear un JugueteListViewModel
        if (modelClass.isAssignableFrom(JugueteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JugueteListViewModel(repository) as T
        }

        // Sabe cómo crear un CreateJugueteViewModel
        if (modelClass.isAssignableFrom(CreateJugueteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateJugueteViewModel(repository) as T
        }

        // Sabe cómo crear un EditJugueteViewModel (y le pasa el SavedStateHandle)
        if (modelClass.isAssignableFrom(EditJugueteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val savedStateHandle = extras.createSavedStateHandle()
            return EditJugueteViewModel(repository, savedStateHandle) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}