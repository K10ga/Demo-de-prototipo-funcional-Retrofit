package mx.edu.utez.prototipojugueteria.data.repository

import android.content.Context
import android.net.Uri
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class JugueteRepository(
    private val apiService: ApiService,
    private val context: Context
) {

    /**
     * Obtiene la lista completa de juguetes desde la API.
     */
    suspend fun getJuguetes(): List<Juguete> {
        return try {
            apiService.getJuguetes()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Obtiene un solo juguete por su ID.
     */
    suspend fun getJugueteById(id: Int): Juguete? {
        return try {
            apiService.getJugueteById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Inserta un nuevo juguete. No devuelve nada.
     * El refresco se maneja desde el Navigation.
     */
    suspend fun insertJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) { // <-- Ya no devuelve Juguete?
        try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val imagePart = uriToMultipart(imageUri)

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

    /**
     * Actualiza un juguete. No devuelve nada.
     * El refresco se maneja desde el Navigation.
     */
    suspend fun updateJuguete(
        id: Int,
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) { // <-- Ya no devuelve Juguete?
        try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val imagePart = uriToMultipart(imageUri)

            apiService.updateJuguete(
                id = id,
                nombre = nombreBody,
                tipoJuguete = tipoBody,
                precio = precioBody,
                image = imagePart
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Elimina un juguete de la API por su ID.
     */
    suspend fun deleteJuguete(id: Int) {
        try {
            apiService.deleteJuguete(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Helper privado para convertir una Uri (Galería/Cámara)
     * a un MultipartBody.Part (lo que Retrofit necesita).
     */
    private fun uriToMultipart(imageUri: Uri?): MultipartBody.Part? {
        if (imageUri == null) return null

        return try {
            val type = context.contentResolver.getType(imageUri)
            val stream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val bytes = stream?.readBytes()
            stream?.close()

            if (bytes != null && type != null) {
                val requestFile = bytes.toRequestBody(type.toMediaTypeOrNull())
                // "image" debe coincidir con el nombre esperado en la API de Flask
                MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}