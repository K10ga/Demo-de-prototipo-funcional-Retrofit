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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.R
import mx.edu.utez.prototipojugueteria.ui.components.ToyCategorySelector
import mx.edu.utez.prototipojugueteria.ui.theme.ToyBlueDeep
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel
import java.io.File

@Composable
fun CreateJugueteScreen(
    viewModel: JugueteViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(3),
        onResult = { uris ->
            if (uris.size > 3) {
                errorMessage = "¡Solo puedes elegir máximo 3 fotos!"
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
                    errorMessage = "¡Límite alcanzado! Máximo 3 fotos."
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
                    errorMessage = "¡Ya tienes 3 fotos!"
                } else {
                    val newUri = createImageUri(context)
                    tempCameraUri = newUri
                    cameraLauncher.launch(newUri)
                }
            }
        }
    )

    // Datos
    var nombre by remember { mutableStateOf("") }
    var tipoJuguete by remember { mutableStateOf("Figuras y Rol") } // Default
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuevo Juguete",
            style = MaterialTheme.typography.headlineMedium,
            color = ToyBlueDeep,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Visor Fotos
        if (selectedImageUris.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(16.dp))
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
            Box(
                Modifier.size(150.dp).clip(CircleShape).background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(model = R.drawable.toyhub, contentDescription = null, modifier = Modifier.padding(20.dp))
            }
            Text("Agrega fotos (Máx 3)", style = MaterialTheme.typography.bodySmall)
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones Fotos
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) { Text("Galería") }
            Button(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) { Text("Cámara") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del juguete") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SELECTOR DE CATEGORÍA (NUEVO)
        ToyCategorySelector(
            selectedCategory = tipoJuguete,
            onCategorySelected = { tipoJuguete = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // PRECIO VALIDADO
        OutlinedTextField(
            value = precio,
            onValueChange = { input ->
                // Solo permite números y un punto decimal
                if (input.all { it.isDigit() || it == '.' } && input.count { it == '.' } <= 1) {
                    precio = input
                }
            },
            label = { Text("Precio ($)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull() ?: 0.0
                if (nombre.isBlank() || precioDouble <= 0) {
                    errorMessage = "Revisa el nombre y precio"
                } else if (selectedImageUris.size > 3) {
                    errorMessage = "Máximo 3 fotos"
                } else {
                    viewModel.addNewJuguete(nombre, tipoJuguete, precioDouble, selectedImageUris)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("¡Publicar Juguete!", fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "images")
    imageDir.mkdirs()
    val file = File(imageDir, "juguete_photo_${System.currentTimeMillis()}.jpg")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}