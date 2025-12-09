package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo ToyHub
        Image(
            painter = painterResource(id = R.drawable.toyhub),
            contentDescription = "Logo ToyHub",
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium, color = ToyBlueDeep, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        UserInputField(viewModel = viewModel, label = "Usuario")
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(viewModel = viewModel, label = "Contraseña")

        if (viewModel.loginError.value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = viewModel.loginError.value, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(navController) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ToyOrange)
        ) {
            Text("Entrar", fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("registro") }) {
            Text("¿Nuevo aquí? Crea tu cuenta", color = ToyBlueDeep)
        }
    }
}