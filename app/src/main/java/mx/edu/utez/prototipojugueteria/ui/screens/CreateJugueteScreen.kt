package mx.edu.utez.prototipojugueteria.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import java.io.File

@Composable
fun CreateJugueteScreen(
    viewModel: JugueteViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    // Estado para las fotos
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Estado para el mensaje de error (si se pasa de 3)
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // --- Launchers ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(3), // Intento de límite nativo (a veces no funciona en todos los Android)
        onResult = { uris ->
            if (uris.size > 3) {
                errorMessage = "¡Solo puedes elegir máximo 3 fotos!"
                // Cortamos la lista a 3
                selectedImageUris = uris.take(3)
            } else {
                selectedImageUris = uris
                errorMessage = null
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && tempCameraUri != null) {
                if (selectedImageUris.size >= 3) {
                    errorMessage = "¡Límite alcanzado! No puedes agregar más de 3 fotos."
                } else {
                    selectedImageUris = selectedImageUris + tempCameraUri!!
                    errorMessage = null
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                if (selectedImageUris.size >= 3) {
                    errorMessage = "¡Ya tienes 3 fotos! Elimina una para tomar otra."
                } else {
                    val newUri = createImageUri(context)
                    tempCameraUri = newUri
                    cameraLauncher.launch(newUri)
                }
            }
        }
    )

    var nombre by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Nuevo Juguete", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // --- Visor de Fotos ---
        if (selectedImageUris.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp))
            ) { page ->
                AsyncImage(
                    model = selectedImageUris[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text("${pagerState.currentPage + 1} / ${selectedImageUris.size}", style = MaterialTheme.typography.bodySmall)
        } else {
            AsyncImage(
                model = null, contentDescription = null,
                placeholder = painterResource(id = R.drawable.loginutez),
                error = painterResource(id = R.drawable.loginutez),
                modifier = Modifier.size(150.dp).clip(CircleShape).background(Color.LightGray)
            )
            Text("Sin fotos", style = MaterialTheme.typography.bodySmall)
        }

        // --- MENSAJE DE ERROR ROJO ---
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text("Galería")
            }
            Button(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) {
                Text("Cámara")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre:") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = tipoJuguete, onValueChange = { tipoJuguete = it }, label = { Text("Tipo:") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio:") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (selectedImageUris.size > 3) {
                    errorMessage = "¡Demasiadas fotos! Máximo 3."
                } else {
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    viewModel.addNewJuguete(nombre, tipoJuguete, precioDouble, selectedImageUris)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar Juguete") }
    }
}

private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "images")
    imageDir.mkdirs()
    val file = File(imageDir, "juguete_photo_${System.currentTimeMillis()}.jpg")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}