package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.ui.components.inputs.PasswordField
import mx.edu.utez.prototipojugueteria.ui.components.inputs.UserInputField
import mx.edu.utez.prototipojugueteria.ui.theme.ToyBlueDeep
import mx.edu.utez.prototipojugueteria.ui.theme.ToyOrange
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel
import mx.edu.utez.prototipojugueteria.viewmodel.RegisterViewModel

@Composable
fun RegistroScreen(viewModel: RegisterViewModel, navController: NavController) {

    // ADAPTADOR TEMPORAL: Para usar tus componentes de input existentes
    // (UserInputField y PasswordField esperan un LoginViewModel)
    val tempLoginVM = remember {
        LoginViewModel().apply {
            username.value = viewModel.username.value
            password.value = viewModel.password.value
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo ToyHub (igual que en Login)
        Image(
            painter = painterResource(id = R.drawable.toyhub),
            contentDescription = "Logo ToyHub",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = ToyBlueDeep,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input Usuario
        UserInputField(viewModel = tempLoginVM, label = "Nuevo Usuario")
        // Sincronizar hacia atrás al ViewModel real
        viewModel.username.value = tempLoginVM.username.value

        Spacer(modifier = Modifier.height(16.dp))

        // Input Contraseña
        PasswordField(viewModel = tempLoginVM, label = "Contraseña")
        // Sincronizar hacia atrás
        viewModel.password.value = tempLoginVM.password.value

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(text = viewModel.errorMessage.value, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (viewModel.isLoading.value) {
            CircularProgressIndicator(color = ToyOrange)
        } else {
            Button(
                onClick = { viewModel.register(navController) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ToyOrange)
            ) {
                Text("Registrarse", fontSize = MaterialTheme.typography.titleMedium.fontSize)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Cancelar / Volver", color = Color.Gray)
        }
    }
}