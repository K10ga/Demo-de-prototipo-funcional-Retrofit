package mx.edu.utez.prototipojugueteria.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.network.ApiService
import mx.edu.utez.prototipojugueteria.data.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.InputStream

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
        imageUris: List<Uri>,
        userId: Int?
    ) {
        try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val userIdBody = (userId ?: 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

            // Usamos el nuevo helper que comprime
            val imageParts = urisToMultipart(imageUris)

            apiService.addJuguete(
                nombre = nombreBody,
                tipoJuguete = tipoBody,
                precio = precioBody,
                userId = userIdBody,
                images = imageParts
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
        imageUris: List<Uri>
    ) {
        try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val tipoBody = (tipoJuguete ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imageParts = urisToMultipart(imageUris)

            apiService.updateJuguete(
                id = id,
                nombre = nombreBody,
                tipoJuguete = tipoBody,
                precio = precioBody,
                images = imageParts
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

    suspend fun comprarJuguete(jugueteId: Int, compradorId: Int) {
        try {
            val body = mapOf("comprador_id" to compradorId)
            apiService.comprarJuguete(jugueteId, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            apiService.registerUser(user)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun login(user: User): Boolean {
        return try {
            val response = apiService.loginUser(user)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    // --- HELPER ACTUALIZADO: COMPRIME LAS IMÁGENES ---
    private fun urisToMultipart(uris: List<Uri>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()

        uris.forEach { uri ->
            try {
                // 1. Abrir el stream
                val stream: InputStream? = context.contentResolver.openInputStream(uri)

                if (stream != null) {
                    // 2. Decodificar a Bitmap (Imagen en memoria)
                    val bitmap = BitmapFactory.decodeStream(stream)
                    stream.close()

                    if (bitmap != null) {
                        // 3. Comprimir el Bitmap (Formato JPEG, Calidad 60%)
                        // Esto reduce drásticamente el tamaño (de 5MB a ~300KB) sin perder mucha calidad visual
                        val bos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos)
                        val byteArray = bos.toByteArray()

                        // 4. Crear el RequestBody con los bytes comprimidos
                        val requestFile = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())

                        // 5. Crear la parte Multipart con nombre único
                        val part = MultipartBody.Part.createFormData(
                            "images",
                            "img_${System.currentTimeMillis()}.jpg",
                            requestFile
                        )
                        parts.add(part)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return parts
    }
}