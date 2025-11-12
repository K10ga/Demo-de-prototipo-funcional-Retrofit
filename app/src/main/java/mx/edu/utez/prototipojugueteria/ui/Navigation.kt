package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Tus pantallas
import mx.edu.utez.prototipojugueteria.ui.screens.CreateJugueteScreen
import mx.edu.utez.prototipojugueteria.ui.screens.ForgotPasswordScreen
import mx.edu.utez.prototipojugueteria.ui.screens.IndexJuguetesScreen
import mx.edu.utez.prototipojugueteria.ui.screens.LoginScreen
import mx.edu.utez.prototipojugueteria.ui.screens.RegistroScreen
// Infraestructura de API
import mx.edu.utez.prototipojugueteria.data.network.RetrofitInstance
import mx.edu.utez.prototipojugueteria.data.repository.JugueteRepository
// --- ¡Los nuevos archivos! ---
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModelFactory
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    // --- 1. Creamos el Repositorio UNA SOLA VEZ ---
    val jugueteRepository = remember {
        JugueteRepository(RetrofitInstance.api, context)
    }

    // --- 2. Creamos la Factory UNA SOLA VEZ ---
    val jugueteFactory = remember(jugueteRepository, context) {
        JugueteViewModelFactory(jugueteRepository, context)
    }

    NavHost(navController = navController, startDestination = "login") {

        // --- Rutas de Usuario (no usan la factory) ---
        composable("login") {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(viewModel, navController)
        }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("registro") { RegistroScreen(navController) }

        // --- Rutas CRUD (ambas usan el MISMO ViewModel) ---

        composable("juguetes") { // Pantalla de Lista
            // 3. Obtenemos el ViewModel (la instancia compartida)
            val viewModel: JugueteViewModel = viewModel(factory = jugueteFactory)
            val items = viewModel.juguetesUiState.collectAsState()

            IndexJuguetesScreen(
                juguetes = items.value,
                onAddClick = { navController.navigate("createJuguete") },
                onJugueteClick = { id -> /* Lógica de Editar (pendiente) */ },
                navController = navController
            )
        }

        composable("createJuguete") { // Pantalla de Crear
            // 3. Obtenemos el ViewModel (la MISMA instancia compartida)
            val viewModel: JugueteViewModel = viewModel(factory = jugueteFactory)

            CreateJugueteScreen(
                viewModel = viewModel, // Se lo pasamos
                navController = navController
            )
        }
    }
}