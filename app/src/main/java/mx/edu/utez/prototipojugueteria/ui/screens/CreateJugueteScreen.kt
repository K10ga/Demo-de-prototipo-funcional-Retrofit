package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.R
// TODO: Descomenta esto cuando tengas tu componente de imagen
// import mx.edu.utez.prototipojugueteria.ui.components.images.CircularImage
import mx.edu.utez.prototipojugueteria.viewmodel.CreateJugueteViewModel

@Composable
fun CreateJugueteScreen(
    viewModel: CreateJugueteViewModel, // <-- ¡Activado! Ya no está comentado.
    nav: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nuevo Juguete")
        Spacer(modifier = Modifier.height(20.dp))

        // TODO: Descomenta esto cuando tengas tu componente de imagen
        // CircularImage(imageRes = R.drawable.loginutez, size = 150)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.nombre, // <-- Conectado al ViewModel
            onValueChange = { viewModel.nombre = it }, // <-- Conectado al ViewModel
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.precio, // <-- Conectado al ViewModel
            onValueChange = { viewModel.precio = it }, // <-- Conectado al ViewModel
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.descripcion, // <-- Conectado al ViewModel
            onValueChange = { viewModel.descripcion = it }, // <-- Conectado al ViewModel
            label = { Text("Descripción (Tipo)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            onClick = {
                viewModel.createJuguete() // <-- Conectado al ViewModel
                nav.navigate("juguetes") { popUpTo("juguetes") { inclusive = true } }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Crear Juguete")
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9E9E9E)
            ),
            onClick = { nav.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}