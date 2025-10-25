package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import mx.edu.utez.prototipojugueteria.ui.components.texts.Title
import mx.edu.utez.prototipojugueteria.ui.components.texts.Label

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