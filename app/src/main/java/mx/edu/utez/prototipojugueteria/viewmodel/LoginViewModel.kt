package mx.edu.utez.prototipojugueteria.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import mx.edu.utez.prototipojugueteria.data.model.User
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository
import mx.edu.utez.prototipojugueteria.utils.UserSession // Asegúrate de haber creado este archivo

class LoginViewModel(private val repository: JugueteRepository? = null) : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // Estados para la UI
    var loginError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun login(navController: NavController) {
        // 1. Validaciones básicas
        if (username.value.isBlank() || password.value.isBlank()) {
            loginError.value = "Escribe usuario y contraseña"
            return
        }

        // 2. Validación de seguridad (por si se usa la instancia "falsa" del registro)
        if (repository == null) {
            loginError.value = "Error interno: Repositorio no inicializado"
            return
        }

        isLoading.value = true
        loginError.value = ""

        viewModelScope.launch {
            try {
                val userToCheck = User(username = username.value, password = password.value)

                // 3. Llamada a la API
                // IMPORTANTE: Para que esto funcione, asegúrate de que en JugueteRepository
                // la variable 'apiService' no sea privada (debe ser 'val apiService: ApiService')
                val response = repository.apiService.loginUser(userToCheck)

                if (response.isSuccessful) {
                    // 4. Procesar respuesta exitosa (200 OK)
                    val body = response.body()

                    // Usamos Gson para convertir la respuesta 'Any' a un Mapa y leer los datos
                    val gson = Gson()
                    val json = gson.toJson(body)
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val data: Map<String, Any> = gson.fromJson(json, mapType)

                    // 5. Extraer y guardar en Sesión
                    val idDouble = data["id"] as? Double
                    val idInt = idDouble?.toInt() // Gson a veces lee números como Double
                    val userStr = data["username"] as? String

                    // Guardamos en el Singleton para usarlo al crear juguetes
                    UserSession.currentUserId = idInt
                    UserSession.currentUsername = userStr

                    // 6. Navegar
                    loginError.value = ""
                    navController.navigate("juguetes") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    // Error 401 o similar
                    loginError.value = "Usuario o contraseña incorrectos"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loginError.value = "Error de conexión con el servidor"
            } finally {
                isLoading.value = false
            }
        }
    }
}