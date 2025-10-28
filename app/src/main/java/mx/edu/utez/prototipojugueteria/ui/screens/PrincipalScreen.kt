package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.ui.components.JugueteList // Componente nuevo
import mx.edu.utez.prototipojugueteria.viewmodel.PrincipalViewModel

@Composable
fun PrincipalScreen(viewModel: PrincipalViewModel, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(30.dp)
    ) {
        Text("Juguetes Registrados") // Equivalente a tu Title
        val juguetes by viewModel.juguetes.collectAsStateWithLifecycle()

        // El LazyColumn adaptado para juguetes
        JugueteList(juguetes) { juguete ->
            // Al hacer clic:
            // 1. Guardamos el juguete en el ViewModel
            viewModel.clickJuguete(juguete)
            // 2. Navegamos a la pantalla de detalle
            navController.navigate("juguete_detail") // Nueva ruta
        }

        Text("No hay más juguetes") // Equivalente a tu Label
    }
}
