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
import androidx.compose.material3.Button
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

    // --- 1. Estado para la LISTA de URIs ---
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Variable temporal para la cámara (guarda la foto actual antes de agregarla a la lista)
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // --- 2. Launchers ---

    // Galería Múltiple
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if (uris.isNotEmpty()) {
                selectedImageUris = uris // Reemplaza o agrega según prefieras
            }
        }
    )

    // Cámara (Toma una a la vez y la agrega a la lista)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && tempCameraUri != null) {
                // Agregamos la foto nueva a la lista existente
                selectedImageUris = selectedImageUris + tempCameraUri!!
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val newUri = createImageUri(context)
                tempCameraUri = newUri
                cameraLauncher.launch(newUri)
            }
        }
    )

    // --- 3. Campos de texto ---
    var nombre by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    // --- 4. Layout ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Nuevo Juguete", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // --- VISOR DE IMÁGENES (Carrusel) ---
        if (selectedImageUris.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) { page ->
                AsyncImage(
                    model = selectedImageUris[page],
                    contentDescription = "Foto seleccionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Indicador de cuántas fotos hay
            Text(
                text = "${pagerState.currentPage + 1} / ${selectedImageUris.size}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else {
            // Placeholder si no hay fotos
            AsyncImage(
                model = null,
                contentDescription = "Placeholder",
                placeholder = painterResource(id = R.drawable.loginutez),
                error = painterResource(id = R.drawable.loginutez),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Text("Sin fotos seleccionadas", style = MaterialTheme.typography.bodySmall)
        }

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
                Text("Galería (Múltiple)")
            }
            Button(onClick = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Cámara (+1)")
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
                val precioDouble = precio.toDoubleOrNull() ?: 0.0

                // Llamamos a la nueva función que acepta LISTA de URIs
                viewModel.addNewJuguete(
                    nombre = nombre,
                    tipoJuguete = tipoJuguete,
                    precio = precioDouble,
                    imageUris = selectedImageUris // <-- Enviamos la lista
                )

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Juguete")
        }
    }
}

// Función auxiliar para la cámara
private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "images")
    imageDir.mkdirs()
    val file = File(imageDir, "juguete_photo_${System.currentTimeMillis()}.jpg")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}