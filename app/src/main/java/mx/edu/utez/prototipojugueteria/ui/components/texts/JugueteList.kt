package mx.edu.utez.prototipojugueteria.ui.components.texts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import mx.edu.utez.prototipojugueteria.data.model.Juguete

@Composable
fun JuegueteList(lista: List<Juguete>, x: (Juguete) -> Unit) {

    //esto se encargar el uzy o carlos depnede si de lo que le toco tiene que ver con las List
    /*
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(999.dp)
    ) {
        items(items = lista, key = {it.id} ) {jueguete ->
            JuegueteCard(jueguete,x)
        }
    }*/
}