package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.utez.prototipojugueteria.data.JugueteriaDataContainer // Importante
import mx.edu.utez.prototipojugueteria.ui.screens.CreateJugueteScreen // La pantalla de la respuesta anterior
import mx.edu.utez.prototipojugueteria.ui.screens.EditJugueteScreen // La pantalla de la respuesta anterior
import mx.edu.utez.prototipojugueteria.ui.screens.ForgotPasswordScreen
import mx.edu.utez.prototipojugueteria.ui.screens.IndexJuguetesScreen // La pantalla de la respuesta anterior
import mx.edu.utez.prototipojugueteria.ui.screens.LoginScreen
import mx.edu.utez.prototipojugueteria.ui.screens.RegistroScreen
import mx.edu.utez.prototipojugueteria.viewmodel.CreateJugueteViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.EditJugueteViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteListViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.ViewModelFactory

@Composable
fun Navigation(jugueteriaDataContainer: JugueteriaDataContainer) { // Recibe el AppContainer
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- Rutas de Usuario (Tus rutas existentes) ---
        composable("login") {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(viewModel, navController)
        }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("registro") { RegistroScreen(navController) }

        // --- Rutas CRUD de Juguetes (Como en Platillos) ---

        composable ("createJuguete") {
            // Crea el ViewModel usando la Factory con el repositorio del AppContainer
            val viewModel: CreateJugueteViewModel = viewModel(
                factory = ViewModelFactory(jugueteriaDataContainer.jugueteRepository)
            )
            // Asegúrate de usar la pantalla 'CreateJugueteScreen' que te di en el paso anterior
            CreateJugueteScreen(viewModel, navController)
        }

        composable(
            "editJuguete/{id}", // Ruta con argumento
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val viewModel: EditJugueteViewModel = viewModel(
                factory = ViewModelFactory(jugueteriaDataContainer.jugueteRepository)
            )
            EditJugueteScreen(viewModel, navController)
        }

        composable("juguetes") { // Pantalla principal de la lista (como "menu")
            val viewModel: JugueteListViewModel = viewModel(
                factory = ViewModelFactory(jugueteriaDataContainer.jugueteRepository)
            )
            val items = viewModel.items.collectAsState()

            IndexJuguetesScreen(
                juguetes = items.value,
                onAddClick = { navController.navigate("createJuguete") }, // Navega a crear
                onJugueteClick = { id -> navController.navigate("editJuguete/$id") }, // Navega a editar
                navController = navController
            )
        }
    }
}