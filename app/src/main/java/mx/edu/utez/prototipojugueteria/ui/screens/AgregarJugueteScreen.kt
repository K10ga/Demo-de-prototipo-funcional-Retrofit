package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel // <-- IMPORTANTE

@Composable
fun AgregarJugueteScreen(
    navController: NavController,
    viewModel: JugueteViewModel
) {


    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // --- CAMPO NOMBRE ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Juguete") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO TIPO ---
        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo de Juguete (Ej. Vehículo)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO PRECIO ---
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio (Ej. 150.0)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // --- BOTÓN CONFIRMAR ---
            Button(onClick = {
                // Convertimos el precio a Float (o null si está mal escrito)
                val precioFloat = precio.toFloatOrNull()

                // Validamos que los campos no estén vacíos
                if (nombre.isNotEmpty() && tipo.isNotEmpty() && precioFloat != null) {

                    // Llamamos a la función (corregida) del ViewModel
                    viewModel.addNewJuguete(

                        nombreJuguete = nombre,
                        tipoJuguete = tipo,
                        precio = precioFloat,
                        imagen = R.drawable.loginutez
                    )

                    // Regresamos a la pantalla anterior
                    navController.popBackStack()
                }
            }) {
                Text("Confirmar")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.loginutez),
                    contentDescription = "Confirmar"
                )
            }

            // --- BOTÓN CANCELAR ---
            Button(onClick = {
                // Simplemente regresa a la pantalla anterior
                navController.popBackStack()
            }) {
                Text("Cancelar")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.loginutez),
                    contentDescription = "Cancelar"
                )
            }
        }
    }
}
