package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.ui.components.buttons.PrimaryButton
import mx.edu.utez.prototipojugueteria.ui.components.inputs.PasswordField
import mx.edu.utez.prototipojugueteria.ui.components.inputs.UserInputField
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel // Necesario para el adaptador
import mx.edu.utez.prototipojugueteria.viewmodel.RegisterViewModel
import androidx.compose.runtime.remember

@Composable
fun RegistroScreen(viewModel: RegisterViewModel, navController: NavController) {

    // ADAPTADOR TEMPORAL: Para usar tus componentes existentes sin modificarlos
    val tempLoginVM = remember {
        LoginViewModel().apply {
            username.value = viewModel.username.value
            password.value = viewModel.password.value
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Input Usuario
        UserInputField(viewModel = tempLoginVM, label = "Usuario")
        // Sincronizar hacia atrás
        viewModel.username.value = tempLoginVM.username.value

        Spacer(modifier = Modifier.height(16.dp))

        // Input Contraseña
        PasswordField(viewModel = tempLoginVM, label = "Contraseña")
        // Sincronizar hacia atrás
        viewModel.password.value = tempLoginVM.password.value

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(viewModel.errorMessage.value, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        } else {
            PrimaryButton("Registrarse") {
                viewModel.register(navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.TextButton(onClick = { navController.popBackStack() }) {
            Text("Cancelar")
        }
    }
}