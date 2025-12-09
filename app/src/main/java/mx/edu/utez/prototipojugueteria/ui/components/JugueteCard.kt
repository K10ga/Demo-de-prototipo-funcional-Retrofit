package mx.edu.utez.prototipojugueteria.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate // <--- IMPORTANTE
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.ui.theme.*

@Composable
fun JugueteCard(j: Juguete, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header con Logo pequeño
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icono categoría (simulado)
                Box(Modifier.size(10.dp).clip(RoundedCornerShape(50)).background(ToyOrange))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = j.tipoJuguete?.uppercase() ?: "JUGUETE",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                if (j.userId != null) {
                    Text("De: ${j.vendedorNombre}", fontSize = 10.sp, color = ToyBlueDeep)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.Top) {
                // FOTO
                Box(modifier = Modifier.weight(0.4f).aspectRatio(1f).clip(RoundedCornerShape(12.dp)).background(Color.LightGray)) {
                    AsyncImage(
                        model = j.imageUrl ?: R.drawable.toyhub,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (j.vendido) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "VENDIDO",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                // CORRECCIÓN AQUÍ: Usar modifier para rotar
                                modifier = Modifier.rotate(-15f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // DATOS
                Column(modifier = Modifier.weight(0.6f)) {
                    Text(text = j.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(Modifier.height(4.dp))
                    Text(text = "$ ${j.precio}", style = MaterialTheme.typography.headlineSmall, color = ToyGreen, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}