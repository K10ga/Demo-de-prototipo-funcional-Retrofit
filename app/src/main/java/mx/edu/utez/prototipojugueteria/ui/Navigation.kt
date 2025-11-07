package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.prototipojugueteria.ui.screens.LoginScreen
import androidx.navigation.compose.composable
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel
import androidx.navigation.compose.NavHost
import mx.edu.utez.prototipojugueteria.ui.screens.AgregarJugueteScreen
import mx.edu.utez.prototipojugueteria.ui.screens.ForgotPasswordScreen

import mx.edu.utez.prototipojugueteria.ui.screens.RegistroScreen
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import androidx.compose.ui.platform.LocalContext
import mx.edu.utez.prototipojugueteria.JugueteriaApp
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModelFactory

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // --- ViewModels ---
    val loginViewModel: LoginViewModel = viewModel()

/*
    // Instanciamos el ViewModel de juguetes para pasarlo a las pantallas
    val context = LocalContext.current
    val application = context.applicationContext as JugueteriaApp

    // Instanciamos el ViewModel usando la Factory para inyectar el repositorio
    val jugueteViewModel: JugueteViewModel = viewModel(
        factory = JugueteViewModelFactory(application.repository)
    )*/
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
       /* composable("agregarjuguete"){
            AgregarJugueteScreen(navController, jugueteViewModel) // <-- Asegúrate de pasar el viewModel
        }*/


/*
        composable("juguete") {
            PrincipalScreen(
                viewModel = jugueteViewModel,
                navController = navController
            )
        }

 */

/*
        composable("juguete_detail") {
            JugueteDetailScreen(
                viewModel = jugueteViewModel,
                navController = navController
            )
        }*/
    }
}