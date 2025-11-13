package mx.edu.utez.prototipojugueteria.ui.screens

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel

@Composable
fun EditJugueteScreen(
    viewModel: JugueteViewModel,
    navController: NavController,
    jugueteId: Int
) {
    val context = LocalContext.current

    // --- 1. Observar el juguete seleccionado del ViewModel ---
    val jugueteState by viewModel.selectedJuguete.collectAsState()
    val juguete = jugueteState

    // --- 2. Estado local para los campos (se llenarán desde el juguete cargado) ---
    var nombre by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var existingImageUrl by remember { mutableStateOf<String?>(null) }

    // --- 3. Cargar el juguete una sola vez ---
    LaunchedEffect(jugueteId) {
        viewModel.loadJuguete(jugueteId)
    }

    // --- 4. Sincronizar el estado local cuando el juguete se cargue ---
    LaunchedEffect(juguete) {
        if (juguete != null) {
            nombre = juguete.nombre
            tipoJuguete = juguete.tipoJuguete ?: ""
            precio = juguete.precio.toString()
            existingImageUrl = juguete.imageUrl
        }
    }

    // --- 5. Limpiar el juguete seleccionado al salir ---
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedJuguete()
        }
    }

    // --- 6. Launchers (Galería, Cámara, Permiso) - Igual que en Create ---
    // (Omitido por brevedad, es idéntico a tu CreateJugueteScreen)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                existingImageUrl = null // Ocultar la imagen existente si se selecciona una nueva
            }
        }
    )
    // (Aquí irían los launchers de Cámara y Permiso, si los usas)

    // --- 7. Layout ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Añadido para scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (juguete == null && existingImageUrl == null) {
            // Muestra un indicador de carga mientras el juguete se obtiene
            CircularProgressIndicator()
            Text("Cargando juguete...")
        } else {
            // Contenido de la pantalla (cuando ya cargó)
            Text("Editar Juguete", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            // Vista previa de la imagen
            AsyncImage(
                // Mostrar la nueva imagen (selectedImageUri) si existe,
                // si no, mostrar la imagen que ya tenía (existingImageUrl)
                model = selectedImageUri ?: existingImageUrl,
                contentDescription = "Foto del juguete",
                placeholder = painterResource(id = R.drawable.loginutez),
                error = painterResource(id = R.drawable.loginutez),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Galería
            Button(onClick = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Cambiar Foto")
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

            // --- Botón de Actualizar ---
            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    viewModel.updateJuguete(
                        id = jugueteId,
                        nombre = nombre,
                        tipoJuguete = tipoJuguete,
                        precio = precioDouble,
                        imageUri = selectedImageUri // Si es null, el repo no manda imagen
                    )
                    navController.popBackStack() // Regresar a la lista
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Actualizar Juguete")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botón de Eliminar ---
            Button(
                onClick = {
                    viewModel.deleteJuguete(jugueteId)
                    navController.popBackStack() // Regresar a la lista
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Eliminar Juguete")
            }
        }
    }
}