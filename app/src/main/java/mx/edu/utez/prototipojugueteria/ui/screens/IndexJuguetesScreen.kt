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
import mx.edu.utez.prototipojugueteria.ui.components.texts.JugueteCard // Importamos JugueteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexJuguetesScreen(
    titulo: String = "Catálogo de Juguetes",
    juguetes: List<Juguete>, // Cambiado a Juguete
    onAddClick: () -> Unit,
    onJugueteClick: (Long) -> Unit, // Cambiado a onJugueteClick
    navController: NavController
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(titulo) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar juguete") // Texto actualizado
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
                Text("Aún no hay juguetes.\nPulsa + para agregar.", textAlign = TextAlign.Center) // Texto actualizado
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = juguetes, key = { it.id }) { juguete -> // Iteramos sobre juguetes
                    JugueteCard(j = juguete) { // Usamos JugueteCard
                        onJugueteClick(juguete.id) // Usamos onJugueteClick
                    }
                }
            }
        }
    }
}