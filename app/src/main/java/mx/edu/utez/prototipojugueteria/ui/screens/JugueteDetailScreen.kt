package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.viewmodel.PrincipalViewModel

@Composable
fun JugueteDetailScreen(viewModel: PrincipalViewModel, navController: NavController) {
    // Obtenemos el juguete seleccionado del ViewModel
    val juguete by viewModel.selectedJuguete.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- Fila de Título y Botón de Regresar (como en StampScreen) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón de Regresar (Icono)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }
            Text(
                text = juguete?.nombreJuguete ?: "Detalle",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (juguete != null) {
            // Contenido de la Card
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- 1. Icono del Juguete ---
                Image(
                    painter = painterResource(id = juguete!!.imagen),
                    contentDescription = juguete!!.nombreJuguete,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )

                // --- 2. Descripción (Atributos) ---
                Text(
                    text = "Descripción del Juguete",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tipo: ${juguete!!.tipoJuguete}",
                    fontSize = 16.sp
                )
                Text(
                    text = "Precio: $${juguete!!.precio}",
                    fontSize = 16.sp
                )

                // Este Spacer empuja los botones hacia el fondo
                Spacer(modifier = Modifier.weight(1f))

                // --- 3. Botones de Editar y Borrar ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón Editar
                    Button(onClick = { /* No hace nada, solo representativo */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Editar")
                    }
                    // Botón Borrar
                    Button(
                        onClick = { /* No hace nada, solo representativo */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Borrar")
                    }
                }

                // --- 4. Botón de Regresar (Abajo) ---
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Regresar")
                }
            }
        } else {
            // Mensaje de error si no hay juguete
            Text(
                text = "Error: No se seleccionó ningún juguete.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}