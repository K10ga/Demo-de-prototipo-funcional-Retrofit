package mx.edu.utez.prototipojugueteria.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.edu.utez.prototipojugueteria.ui.theme.*

@Composable
fun ToyCategorySelector(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Figuras y Rol", "Construcción", "Arte y Educación")

    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Tipo de Juguete:",
            style = MaterialTheme.typography.labelLarge,
            color = ToyBlueDeep,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))

        categories.forEach { category ->
            val isSelected = (category == selectedCategory)

            OutlinedButton(
                onClick = { onCategorySelected(category) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) ToyBlueSky else Color.White,
                    contentColor = if (isSelected) Color.White else ToyBlueDeep
                ),
                border = androidx.compose.foundation.BorderStroke(2.dp, if(isSelected) ToyBlueDeep else ToyBlueSky)
            ) {
                Text(
                    text = category,
                    fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}