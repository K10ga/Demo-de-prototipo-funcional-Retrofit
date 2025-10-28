package mx.edu.utez.prototipojugueteria.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.prototipojugueteria.R

@Composable
fun AgregarJugueteScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .size(150.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Imagen")
        }

        Spacer(modifier = Modifier.height(8.dp))


        Button(onClick = {  }) {
            Icon(
                painter = painterResource(id = R.drawable.loginutez),
                contentDescription = "Subir"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Subir Imagen")
        }

        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {  }) {
                Text("Confirmar")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.loginutez),
                    contentDescription = "Confirmar"
                )
            }

            Button(onClick = {  }) {
                Text("Cancelar")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.loginutez),
                    contentDescription = "Cancelar"
                )
            }
        }
    }
}
