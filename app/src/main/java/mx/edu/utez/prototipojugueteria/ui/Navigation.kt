package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.prototipojugueteria.ui.screens.LoginScreen
import androidx.navigation.compose.composable
// Importa el ViewModel que creamos (el de tu PDF)
import mx.edu.utez.prototipojugueteria.viewmodel.PrincipalViewModel // <-- AÑADIDO
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel
import androidx.navigation.compose.NavHost
import mx.edu.utez.prototipojugueteria.ui.screens.AgregarJugueteScreen
import mx.edu.utez.prototipojugueteria.ui.screens.ForgotPasswordScreen
// Importa las nuevas pantallas que te pasé
import mx.edu.utez.prototipojugueteria.ui.screens.JugueteDetailScreen // <-- AÑADIDO
import mx.edu.utez.prototipojugueteria.ui.screens.PrincipalScreen // <-- AÑADIDO
import mx.edu.utez.prototipojugueteria.ui.screens.RegistroScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // --- ViewModels ---
    val loginViewModel: LoginViewModel = viewModel()

    // Instanciamos el ViewModel de juguetes para pasarlo a las pantallas
    val principalViewModel: PrincipalViewModel = viewModel() // <-- AÑADIDO

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("agregarjuguete"){ AgregarJugueteScreen(navController) }


        // --- RUTAS FALTANTES ---

        // (1) La ruta "juguete" que te da el error.
        // Esta será tu pantalla principal (la lista de juguetes)
        composable("juguete") { // <-- AÑADIDO
            PrincipalScreen(
                viewModel = principalViewModel,
                navController = navController
            )
        }

        // (2) La ruta para la pantalla de detalle (la "card" con botones)
        // La llamé "juguete_detail" en el código que te di antes.
        composable("juguete_detail") { // <-- AÑADIDO
            JugueteDetailScreen(
                viewModel = principalViewModel,
                navController = navController
            )
        }
    }
}