package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val jugueteViewModel: JugueteViewModel = viewModel()

    NavHots(navController = navController, startDestination = "login"){
        composable("login"){


        }
    }

}