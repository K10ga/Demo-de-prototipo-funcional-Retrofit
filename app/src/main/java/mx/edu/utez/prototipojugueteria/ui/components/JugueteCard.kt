package mx.edu.utez.prototipojugueteria.ui.components

// Importa los componentes de Compose
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Importa Coil (AsyncImage) y tu modelo de Juguete
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R // Importa tu R local
import mx.edu.utez.prototipojugueteria.data.model.Juguete

/**
 * Esta es la tarjeta adaptada para Juguetes, basada en tu PetCard.
 * Recibe el Juguete y una función onClick.
 */
@Composable
fun JugueteCard(
    j: Juguete, // Cambiado a 'j' para Juguete
    modifier: Modifier = Modifier,
    onClick: () -> Unit // Añadido para que la tarjeta sea clickeable
) {

    // Color de fondo (puedes cambiarlo)
    val cardBgColor = Color(0xFFF5F8F0)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Reducido el padding vertical
            .clickable { onClick() }, // Se llama al onClick
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- 1. Encabezado ---
            JugueteHeader() // Helper adaptado

            Spacer(modifier = Modifier.height(16.dp))

            // --- 2. Contenido Principal (Foto y Datos) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // --- Columna Izquierda (Foto) ---
                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- AsyncImage para cargar la URL ---
                    AsyncImage(
                        model = j.imageUrl, // <-- Dato de Juguete
                        contentDescription = "Foto de ${j.nombre}", // <-- Dato de Juguete
                        // Placeholder (usa tu imagen de login)
                        placeholder = painterResource(id = R.drawable.loginutez),
                        error = painterResource(id = R.drawable.loginutez),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // --- Columna Derecha (Datos) ---
                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    // --- Campos de Datos Adaptados ---
                    DataField("ID Juguete:", j.id.toString())
                    DataField("Nombre:", j.nombre.uppercase())
                    DataField("Tipo:", j.tipoJuguete ?: "No especificado")
                    DataField("Precio:", "$ ${j.precio}")
                }
            }
        }
    }
}

/**
 * Helper privado para el encabezado de la tarjeta
 */
@Composable
private fun JugueteHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginutez), // Tu logo
            contentDescription = "Logo",
            modifier = Modifier.size(50.dp)
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "JUGUETE", // <-- Adaptado
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 2.sp
            )
            Text(
                text = "CATÁLOGO", // <-- Adaptado
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}

/**
 * Helper privado para los campos de texto (Este no cambió)
 */
@Composable
private fun DataField(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = label.uppercase(),
            fontSize = 9.sp,
            color = Color.Gray,
            letterSpacing = 0.5.sp
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}