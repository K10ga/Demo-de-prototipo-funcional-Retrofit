package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.ui.components.JugueteCard // Asegúrate de que esta sea la ruta correcta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexJuguetesScreen(
    // titulo: String = "Catálogo de Juguetes", // <-- 1. Eliminamos esto
    juguetes: List<Juguete>,
    onAddClick: () -> Unit,
    onJugueteClick: (Int) -> Unit, // <-- 2. Cambiado a Int
    navController: NavController
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo de Juguetes") }) }, // Ponemos el título aquí
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar juguete")
            }
        }
    ) { padding ->
        if (juguetes.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no hay juguetes.\nPulsa + para agregar.", textAlign = TextAlign.Center)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = juguetes, key = { it.id }) { juguete ->
                    JugueteCard(j = juguete) {
                        onJugueteClick(juguete.id) // <-- Ahora coincide (Int) -> (Int)
                    }
                }
            }
        }
    }
}