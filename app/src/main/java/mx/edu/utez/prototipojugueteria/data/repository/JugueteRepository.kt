package mx.edu.utez.prototipojugueteria.data.repository

import android.content.Context
import android.net.Uri
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import mx.edu.utez.prototipojugueteria.data.model.User

class JugueteRepository(
    val apiService: ApiService,
    private val context: Context
) {

    suspend fun getJuguetes(): List<Juguete> {
        return try {
            apiService.getJuguetes()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getJugueteById(id: Int): Juguete? {
        return try {
            apiService.getJugueteById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun insertJuguete(
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?,
        userId: Int?
    ) {
        try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            // Convertimos el ID a RequestBody
            val userIdBody = (userId ?: 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = uriToMultipart(imageUri)

            // --- AQUÍ ESTABA EL ERROR ---
            // Cambiamos "user_id =" por "userId =" para que coincida con ApiService
            apiService.addJuguete(
                nombre = nombreBody,
                tipoJuguete = tipoBody,
                precio = precioBody,
                userId = userIdBody, // <--- CORREGIDO (userId sin guion bajo)
                image = imagePart
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateJuguete(
        id: Int,
        nombre: String,
        tipoJuguete: String?,
        precio: Double,
        imageUri: Uri?
    ) {
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

    suspend fun deleteJuguete(id: Int) {
        try {
            apiService.deleteJuguete(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            apiService.registerUser(user)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun login(user: User): Boolean {
        return try {
            val response = apiService.loginUser(user)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun uriToMultipart(imageUri: Uri?): MultipartBody.Part? {
        if (imageUri == null) return null

        return try {
            val type = context.contentResolver.getType(imageUri)
            val stream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val bytes = stream?.readBytes()
            stream?.close()

            if (bytes != null && type != null) {
                val requestFile = bytes.toRequestBody(type.toMediaTypeOrNull())
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