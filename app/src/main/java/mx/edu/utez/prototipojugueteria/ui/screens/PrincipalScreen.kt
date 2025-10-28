package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.prototipojugueteria.viewmodel.PrincipalViewModel
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.R

@Composable
fun PrincipalScreen(viewModel: PrincipalViewModel = viewModel()) {
    val juguete by viewModel.juguetes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchText,
            onValueChange = {  },
            placeholder = { Text("Buscar juguete") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Rejilla de juguetes
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(juguetes) { juguete ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    onClick = { viewModel.clickJuguete(juguete) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = juguete.imagen),
                            contentDescription = juguete.nombreJuguete,
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = juguete.nombreJuguete)
                        Text(text = juguete.tipoJuguete)
                        Text(text = "$${juguete.precio}")
                    }
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
            IconButton(onClick = { /* fitros */ }) {
                Icon(Icons.Default.Tune, contentDescription = "filtros")
            }
            IconButton(onClick = { /* Agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
            IconButton(onClick = { login }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPrincipalScreen() {
    val viewModel = PrincipalViewModel()
    PrincipalScreen(viewModel)
}
