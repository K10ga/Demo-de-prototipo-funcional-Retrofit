package mx.edu.utez.prototipojugueteria.ui.components
/*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.prototipojugueteria.data.model.Juguete


@Composable
fun JugueteList(lista: List<Juguete>, onJugueteClick: (Juguete) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = lista, key = { it.id }) { juguete ->
            JugueteCard(juguete, onJugueteClick)
        }
    }
}


@Composable
fun JugueteCard(juguete: Juguete, onJugueteClick: (Juguete) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onJugueteClick(juguete) }, // Hace la tarjeta clickeable
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = juguete.imagen),
                contentDescription = "Imagen de ${juguete.nombreJuguete}",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = juguete.nombreJuguete,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = juguete.tipoJuguete,
                    fontSize = 16.sp
                )
            }
        }
    }
}

 */