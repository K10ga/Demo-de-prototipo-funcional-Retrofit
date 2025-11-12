package mx.edu.utez.prototipojugueteria.data.network

import mx.edu.utez.prototipojugueteria.data.model.Juguete // Importa el Juguete adaptado
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("juguetes") // Cambiado a endpoint de juguetes
    suspend fun getJuguetes(): List<Juguete>

    @Multipart
    @POST("juguetes") // Cambiado a endpoint de juguetes
    suspend fun addJuguete(
        // Partes para los campos del juguete
        @Part("nombre") nombre: RequestBody,
        @Part("tipoJuguete") tipoJuguete: RequestBody,
        @Part("precio") precio: RequestBody, // El precio (Double) también se envía como RequestBody

        // Parte para la imagen
        @Part image: MultipartBody.Part?
    ): Juguete
}