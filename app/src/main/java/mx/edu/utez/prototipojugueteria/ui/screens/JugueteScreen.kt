package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import mx.edu.utez.prototipojugueteria.ui.components.texts.Title
import mx.edu.utez.prototipojugueteria.ui.components.texts.Label


@Composable
fun JugueteScreen(viewModel: JugueteViewModel){

    val juguetes by viewModel.JugueteUiState.collectAsState()
    var id by remember { mutableStateOf("") }
    var nombrejuguete by remember {mutableStateOf("")}
    var tipojuguete by remember { mutableStateOf("") }
    var precio by remember {mutableStateOf("")}

    Column (modifier = Modifier.fillMaxSize()){
        LazyColumn (modifier = Modifier.weight(1f)){

            TextField(value = id, onValueChange = {id = it}, label = {Text("ID")}, singleLine = true)

            TextField(value = nombrejuguete, onValueChange = {id = it}, label = {Text("ID")}, singleLine = true)

            TextField(value = tipojuguete, onValueChange = {id = it}, label = {Text("ID")}, singleLine = true)

            TextField(value = precio, onValueChange = {id = it}, label = {Text("ID")}, singleLine = true)

            Button(onClick = {
                viewModel.addNewJuguete(id,nombrejuguete,tipojuguete,precio)
                id=""
                nombrejuguete=""
                tipojuguete=""
                precio=""
            }) {
                Text("Guardar Juguete")
            }
        }
    }
}



/*
@Composable
fun JugueteScreen(viewModel: JugueteViewModel, navController: NavController){
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(30.dp)
    ) {
        Title("Jueguetes registrados")
        //esto puede que uziel o carlitos tenga que encargarse del jugueteList para que esta parte funcione
        /*
        val jueguetes by viewModel.juguetes.collectAsStateWithLifecycle()
        JueguetesList(jueguetes) { jueguete ->
            viewModel.clickJueguete(jueguetes)
            navController.navigate("")
        }*/
        Label("No hay más pasaportes")
    }
}
*/
