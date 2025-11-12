package mx.edu.utez.prototipojugueteria.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R // Asegúrate de tener un drawable (ej. el logo)
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel // <-- ¡Importa el VM unificado!
import java.io.File
import androidx.compose.material3.MaterialTheme

@Composable
fun CreateJugueteScreen(
    viewModel: JugueteViewModel, // <-- 1. Recibe el JugueteViewModel unificado
    navController: NavController
) {
    val context = LocalContext.current

    // --- 2. Estado para la URI de la imagen ---
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // --- 3. Launchers (Galería, Cámara, Permiso) ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                selectedImageUri = cameraImageUri
            }
        }
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val newUri = createImageUri(context)
                cameraImageUri = newUri
                cameraLauncher.launch(newUri)
            }
        }
    )

    // --- 4. Estado para los campos de texto ---
    var nombre by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") } // Usar String para el TextField

    // --- 5. Layout (copiado de PetScreen) ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Nuevo Juguete", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Vista previa de la imagen
        AsyncImage(
            model = selectedImageUri,
            contentDescription = "Foto del nuevo juguete",
            placeholder = painterResource(id = R.drawable.loginutez), // Cambia a tu placeholder
            error = painterResource(id = R.drawable.loginutez),       // Cambia a tu placeholder
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de imagen
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Galería")
            }
            Button(onClick = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Tomar Foto")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de texto
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = tipoJuguete,
            onValueChange = { tipoJuguete = it },
            label = { Text("Tipo de Juguete:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Botón de Guardar
        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull() ?: 0.0 // Convertir a Double

                // --- 6. Llamar al ViewModel unificado ---
                viewModel.addNewJuguete(
                    nombre = nombre,
                    tipoJuguete = tipoJuguete,
                    precio = precioDouble,
                    imageUri = selectedImageUri
                )

                // Regresar a la pantalla anterior
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Juguete")
        }
    }
}

// --- Función auxiliar para la cámara (igual que en PetScreen) ---
private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "images")
    imageDir.mkdirs()
    val file = File(imageDir, "juguete_photo_${System.currentTimeMillis()}.jpg")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}