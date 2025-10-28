package mx.edu.utez.prototipojugueteria.ui.components.texts


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.R

data class Juguete(val id: Int, val nombre: String, val imagen: Int)

@Composable
fun JugueteCard(juguete: Juguete, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen de juguete (placeholder)
            Image(
                painter = painterResource(id = juguete.imagen),
                contentDescription = "Imagen de ${juguete.nombre}",
                modifier = Modifier
                    .size(60.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = juguete.nombre)
        }
    }
}

@Composable
fun ListaJuguetesScreen(navController: NavController) {
    val juguetes = listOf(
        Juguete(1, "Toy 1", R.drawable.loginutez),
        Juguete(2, "Toy 2", R.drawable.loginutez),
        Juguete(3, "Toy 3", R.drawable.loginutez),
        Juguete(4, "Toy 4", R.drawable.loginutez),
        Juguete(5, "Toy 5", R.drawable.loginutez),
        Juguete(6, "Toy 6", R.drawable.loginutez)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de busqueda (solo decorativo)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Buscar Juguete") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.loginutez),
                    contentDescription = "Buscar"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cuadrícula de juguetes
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f)
        ) {
            items(juguetes) { juguete ->
                JugueteCard(juguete = juguete) {
                    // Navegar a AgregarJugueteScreen
                    navController.navigate("agregarjuguete")
                }
            }
        }

        // Barra inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {  }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menú")
            }
            Button(
                onClick = { navController.navigate("agregarjuguete") },
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
        }
    }
}

