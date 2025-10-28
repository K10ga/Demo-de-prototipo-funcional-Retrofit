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



        composable("juguete") {
            PrincipalScreen(
                viewModel = principalViewModel,
                navController = navController
            )
        }


        composable("juguete_detail") {
            JugueteDetailScreen(
                viewModel = principalViewModel,
                navController = navController
            )
        }
    }
}