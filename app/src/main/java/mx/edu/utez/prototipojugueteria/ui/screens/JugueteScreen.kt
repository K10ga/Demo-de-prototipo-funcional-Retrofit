package mx.edu.utez.prototipojugueteria.ui.screens

/*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import androidx.compose.foundation.lazy.items


@Composable
fun JugueteScreen(viewModel: JugueteViewModel){

    // Estado para la lista de juguetes
    val juguetes by viewModel.JugueteUiState.collectAsState()


    var nombreJuguete by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()

    ){
        // Muestra la lista de juguetes
        LazyColumn (modifier = Modifier.weight(1f)){
            items(juguetes){ juguete ->
                Text(text = " ${juguete.nombreJuguete} (${juguete.tipoJuguete}) - $${juguete.precio}")
            }
        }



        TextField(value = nombreJuguete, onValueChange = {nombreJuguete = it}, label = {Text("nombreJuguete")}, singleLine = true)

        TextField(value = tipoJuguete, onValueChange = {tipoJuguete = it}, label = {Text("tipoJuguete")}, singleLine = true)

        TextField(value = precio, onValueChange = {precio = it}, label = {Text("precio")}, singleLine = true)

        Button(onClick = {
            val precioFloat = precio.toFloatOrNull() ?: 0f
            val imagenInt = imagen.toIntOrNull() ?: 0
            viewModel.addNewJuguete(nombreJuguete, tipoJuguete,precioFloat, imagenInt)
            nombreJuguete =""
            tipoJuguete=""
            precio=""
            imagen=""
        }) {
            Text("Guardar Juguete")
        }


    }
}

 */