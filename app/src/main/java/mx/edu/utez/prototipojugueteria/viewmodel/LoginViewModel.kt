package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.User
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository

// CAMBIO 1: Agregamos "? = null" para permitir crear el ViewModel vacío en RegistroScreen
class LoginViewModel(private val repository: JugueteRepository? = null) : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var loginError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun login(navController: NavController) {
        if (username.value.isBlank() || password.value.isBlank()) {
            loginError.value = "Escribe usuario y contraseña"
            return
        }

        // Seguridad: Si por alguna razón el repo es nulo (no debería pasar en el Login real), salimos.
        if (repository == null) {
            loginError.value = "Error interno: Repositorio no inicializado"
            return
        }

        isLoading.value = true
        loginError.value = ""

        viewModelScope.launch {
            val userToCheck = User(username = username.value, password = password.value)

            // CAMBIO 2: Usamos 'repository.login' directamente (ya validamos que no es null arriba)
            // o usamos el operador safe call por si acaso.
            val success = repository.login(userToCheck)

            isLoading.value = false

            if (success) {
                loginError.value = ""
                navController.navigate("juguetes") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                loginError.value = "Usuario o contraseña incorrectos"
            }
        }
    }
}