package mx.edu.utez.prototipojugueteria.data.repository

import android.content.Context
import android.net.Uri
import mx.edu.utez.prototipojugueteria.data.model.Juguete
// import mx.edu.utez.prototipojugueteria.data.model.JugueteDao // Ya no se usa DAO
import mx.edu.utez.prototipojugueteria.data.network.ApiService // Se usa ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

// 1. El repositorio ahora depende de ApiService y Context, no de JugueteDao
class JugueteRepository(
    private val apiService: ApiService,
    private val context: Context
) {

    // 2. Función para OBTENER todos los juguetes desde la API
    suspend fun getJuguetes(): List<Juguete> {
        return try {
            apiService.getJuguetes()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // 3. Función para INSERTAR un juguete usando la API (Multipart)
    // Adaptado de tu PetRepository
    suspend fun insertJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) {
        try {
            // Convertir Strings y Double a RequestBody
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            // Convertir la Uri de la imagen a MultipartBody.Part
            var imagePart: MultipartBody.Part? = null
            if (imageUri != null) {
                val type = context.contentResolver.getType(imageUri)
                val stream: InputStream? = context.contentResolver.openInputStream(imageUri)
                val bytes = stream?.readBytes()
                stream?.close()

                if (bytes != null && type != null) {
                    val requestFile = bytes.toRequestBody(type.toMediaTypeOrNull())
                    // 'image' debe coincidir con el nombre en el backend
                    imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
                }
            }

            // Llamar a la API con los datos del juguete
            apiService.addJuguete(
                nombre = nombreBody,
                tipoJuguete = tipoBody,
                precio = precioBody,
                image = imagePart
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 4. Los métodos antiguos de Room (listar, getById, actualizar, eliminar)
    // deben ser reemplazados por sus equivalentes de API (ej. updateJuguete, deleteJuguete).
    // Por ahora, getJuguetes e insertJuguete son el inicio.
}