package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.User
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository
import androidx.navigation.NavController

class RegisterViewModel(private val repository: JugueteRepository) : ViewModel() {

    // Usamos la misma estructura que LoginViewModel para ser compatible con tus Inputs
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun register(navController: NavController) {
        if (username.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Campos vacíos"
            return
        }

        isLoading.value = true
        errorMessage.value = ""

        viewModelScope.launch {
            val user = User(username.value, password.value)
            val success = repository.registerUser(user)

            isLoading.value = false
            if (success) {
                // Registro exitoso, volvemos al login
                navController.popBackStack()
            } else {
                errorMessage.value = "Error al registrar. ¿Usuario duplicado?"
            }
        }
    }
}