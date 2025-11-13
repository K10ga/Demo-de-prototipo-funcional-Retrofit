package mx.edu.utez.prototipojugueteria.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

// --- ¡Importar la nueva pantalla de edición! ---
import mx.edu.utez.prototipojugueteria.ui.screens.EditJugueteScreen

// --- ¡¡IMPORTS NECESARIOS PARA EL REFRESCO!! ---
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle

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

        composable("juguetes") { navBackStackEntry -> // <-- 1. Obtener el navBackStackEntry
            // 3. Obtenemos el ViewModel (la instancia compartida)
            val viewModel: JugueteViewModel = viewModel(factory = jugueteFactory)
            val items = viewModel.juguetesUiState.collectAsState()

            // --- 2. LA SOLUCIÓN DE REFRESCO ---
            // Observamos el estado del ciclo de vida de esta pantalla
            val lifecycleState = navBackStackEntry.lifecycle.currentState
            LaunchedEffect(lifecycleState) {
                // Cuando el estado cambia a "RESUMED" (es decir, volvemos a esta pantalla)
                if (lifecycleState == Lifecycle.State.RESUMED) {
                    // Forzamos un refresco de los datos
                    viewModel.fetchJuguetes()
                }
            }
            // --- FIN DE LA SOLUCIÓN ---

            IndexJuguetesScreen(
                juguetes = items.value,
                onAddClick = { navController.navigate("createJuguete") },
                onJugueteClick = { id ->
                    navController.navigate("editJuguete/$id")
                },
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

        // --- NUEVA RUTA: Pantalla de Edición ---
        composable(
            route = "editJuguete/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            // Obtenemos el ID de la ruta
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            // Obtenemos la MISMA instancia del ViewModel
            val viewModel: JugueteViewModel = viewModel(factory = jugueteFactory)

            EditJugueteScreen(
                viewModel = viewModel,
                navController = navController,
                jugueteId = id // Pasamos el ID a la pantalla
            )
        }
    }
}