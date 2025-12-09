package mx.edu.utez.prototipojugueteria.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.ui.components.ToyCategorySelector
import mx.edu.utez.prototipojugueteria.ui.theme.*
import mx.edu.utez.prototipojugueteria.utils.UserSession
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel

@Composable
fun EditJugueteScreen(viewModel: JugueteViewModel, navController: NavController, jugueteId: Int) {
    // Observamos el juguete y la alerta de cambios
    val jugueteState by viewModel.selectedJuguete.collectAsState()
    val showDataChangedAlert by viewModel.dataChangedAlert.collectAsState()

    val context = LocalContext.current

    // Datos locales (lo que ve el usuario en pantalla)
    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var newPhotosUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    LaunchedEffect(jugueteId) { viewModel.loadJuguete(jugueteId) }

    LaunchedEffect(jugueteState) {
        jugueteState?.let {
            // Solo actualizamos los campos si NO estamos editando (para no borrar lo que escribes)
            // O si hubo una alerta de cambio desde el servidor
            if (nombre.isEmpty() || showDataChangedAlert) nombre = it.nombre
            if (tipo.isEmpty() || showDataChangedAlert) tipo = it.tipoJuguete ?: ""
            if (precio.isEmpty() || showDataChangedAlert) precio = it.precio.toString()
        }
    }

    DisposableEffect(Unit) { onDispose { viewModel.clearSelectedJuguete() } }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
        if (uris.isNotEmpty()) newPhotosUris = uris.take(3)
    }

    if (jugueteState == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        val j = jugueteState!!
        val isOwner = (j.userId == UserSession.currentUserId)
        val isSold = j.vendido

        Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {

            // --- Carrusel ---
            val allImages: List<Any> = if (newPhotosUris.isNotEmpty()) newPhotosUris else j.imagenesUrls
            if (allImages.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { allImages.size })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(16.dp))) { page ->
                    AsyncImage(model = allImages[page], contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                }
                Text("${pagerState.currentPage + 1}/${allImages.size}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Box(Modifier.fillMaxWidth().height(250.dp).background(ToyBlueSky.copy(alpha = 0.3f)), contentAlignment = Alignment.Center) {
                    Text("Sin fotos", color = ToyBlueDeep)
                }
            }

            Spacer(Modifier.height(16.dp))

            if (isSold) {
                Card(colors = CardDefaults.cardColors(containerColor = ToyRed), modifier = Modifier.fillMaxWidth()) {
                    Text("¡VENDIDO!", color = Color.White, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally))
                }
                Spacer(Modifier.height(16.dp))
            }

            // --- Campos ---
            val canEdit = isOwner && !isSold

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                enabled = canEdit,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = ToyOrange, focusedLabelColor = ToyOrange)
            )

            Spacer(Modifier.height(8.dp))

            if (canEdit) {
                ToyCategorySelector(selectedCategory = tipo, onCategorySelected = { tipo = it })
            } else {
                OutlinedTextField(value = tipo, onValueChange = {}, label = { Text("Categoría") }, enabled = false, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { if (canEdit && it.all { c -> c.isDigit() || c == '.' }) precio = it },
                label = { Text("Precio") },
                enabled = canEdit,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            if (canEdit) {
                Spacer(Modifier.height(8.dp))
                Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                    Text("Cambiar Fotos (Máx 3)")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- BOTONES ---
            if (isOwner) {
                if (!isSold) {
                    Button(
                        onClick = {
                            viewModel.updateJuguete(jugueteId, nombre, tipo, precio.toDoubleOrNull()?:0.0, newPhotosUris)
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ToyGreen)
                    ) { Text("Guardar Cambios") }
                    Spacer(Modifier.height(12.dp))
                }
                Button(
                    onClick = { viewModel.deleteJuguete(jugueteId); navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ToyRed)
                ) { Text("Eliminar Juguete") }
            } else {
                if (!isSold) {
                    Button(
                        onClick = {
                            // AQUÍ LLAMAMOS A LA NUEVA FUNCIÓN QUE VERIFICA ANTES DE COMPRAR
                            viewModel.attemptPurchase(
                                jugueteId = jugueteId,
                                precioVisto = precio.toDoubleOrNull() ?: 0.0,
                                nombreVisto = nombre,
                                tipoVisto = tipo
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ToyOrange)
                    ) { Text("COMPRAR AHORA", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }

    // --- ALERTA DE DATOS ACTUALIZADOS ---
    if (showDataChangedAlert) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDataChangedAlert() },
            title = { Text("¡Información Actualizada!") },
            text = { Text("El vendedor ha modificado los datos de este juguete (precio, nombre o estado) mientras lo veías.\n\nLos datos se han actualizado en tu pantalla. Por favor, revísalos antes de decidir comprar.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissDataChangedAlert() },
                    colors = ButtonDefaults.buttonColors(containerColor = ToyBlueDeep)
                ) { Text("Entendido, revisar") }
            },
            containerColor = Color.White
        )
    }
}