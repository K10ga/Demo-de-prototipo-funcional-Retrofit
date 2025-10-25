package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.prototipojugueteria.ui.screens.LoginScreen
import androidx.navigation.compose.composable
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel
import androidx.navigation.compose.NavHost
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val loginViewModel: LoginViewModel = viewModel()

    //val jugueteViewModel: JugueteViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }

            //Aqui va el juguete screen

        //pendiente eso depende de uzy y carlitos

    }
}