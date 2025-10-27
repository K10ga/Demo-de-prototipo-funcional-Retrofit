package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.ui.components.buttons.PrimaryButton
import mx.edu.utez.prototipojugueteria.ui.components.inputs.UserInputField

@Composable
fun RegistroScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla de Registro")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Usuario")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Contraseña")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Correo")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Confirmar Contraseña")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton("Volver al Login") {
            navController.popBackStack()
        }
    }
}