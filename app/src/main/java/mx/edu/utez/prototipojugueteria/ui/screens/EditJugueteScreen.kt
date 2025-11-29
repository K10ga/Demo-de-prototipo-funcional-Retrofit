package mx.edu.utez.prototipojugueteria.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.prototipojugueteria.utils.UserSession
import mx.edu.utez.prototipojugueteria.viewmodel.JugueteViewModel

@Composable
fun EditJugueteScreen(viewModel: JugueteViewModel, navController: NavController, jugueteId: Int) {
    val juguete by viewModel.selectedJuguete.collectAsState()

    // Datos locales
    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    // Lista de Uris para NUEVAS fotos
    var newPhotosUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    LaunchedEffect(jugueteId) { viewModel.loadJuguete(jugueteId) }

    LaunchedEffect(juguete) {
        juguete?.let {
            nombre = it.nombre
            tipo = it.tipoJuguete ?: ""
            precio = it.precio.toString()
        }
    }
    DisposableEffect(Unit) { onDispose { viewModel.clearSelectedJuguete() } }

    // Launcher Múltiple
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        if (uris.isNotEmpty()) newPhotosUris = uris
    }

    // --- UI ---
    if (juguete == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        val j = juguete!!
        val isOwner = (j.userId == UserSession.currentUserId)
        val isSold = j.vendido

        Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {

            // --- CARRUSEL DE FOTOS ---
            // Mezclamos las fotos existentes (URLs) con las nuevas seleccionadas (URIs)
            val allImages: List<Any> = if (newPhotosUris.isNotEmpty()) newPhotosUris else j.imagenesUrls

            if (allImages.isNotEmpty()) {
                val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { allImages.size })
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp))
                ) { page ->
                    AsyncImage(
                        model = allImages[page],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                // Indicador simple (1/3)
                Text(
                    text = "${pagerState.currentPage + 1}/${allImages.size}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                // Placeholder si no hay nada
                Box(Modifier.fillMaxWidth().height(250.dp).background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Text("Sin fotos")
                }
            }

            Spacer(Modifier.height(16.dp))

            // --- ESTADO VENDIDO ---
            if (isSold) {
                Text(
                    text = "VENDIDO a ${j.compradorNombre ?: "Alguien"}",
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
            }

            // --- CAMPOS ---
            // Si soy dueño y NO está vendido, puedo editar. Si no, solo lectura.
            val canEdit = isOwner && !isSold

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, enabled = canEdit, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo") }, enabled = canEdit, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, enabled = canEdit, modifier = Modifier.fillMaxWidth())

            if (canEdit) {
                Spacer(Modifier.height(8.dp))
                Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                    Text("Seleccionar Fotos (Reemplaza actuales)")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- LÓGICA DE BOTONES ---
            if (isOwner) {
                // DUEÑO: Eliminar o Guardar Cambios
                if (!isSold) {
                    Button(
                        onClick = {
                            viewModel.updateJuguete(jugueteId, nombre, tipo, precio.toDoubleOrNull()?:0.0, newPhotosUris)
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Guardar Cambios") }

                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        viewModel.deleteJuguete(jugueteId)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text("Eliminar Juguete") }

            } else {
                // NO DUEÑO: Comprar
                if (!isSold) {
                    Button(
                        onClick = { viewModel.comprarJuguete(jugueteId) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) { Text("COMPRAR") }
                }
            }
        }
    }
}