package mx.edu.utez.prototipojugueteria.data.network

import mx.edu.utez.prototipojugueteria.data.model.Juguete
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response // Importar Response para el delete
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
// Imports añadidos
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
//nuevo
import mx.edu.utez.prototipojugueteria.data.model.User
import retrofit2.http.Body



interface ApiService {

    @GET("juguetes")
    suspend fun getJuguetes(): List<Juguete>

    @Multipart
    @POST("juguetes")
    suspend fun addJuguete(
        @Part("nombre") nombre: RequestBody,
        @Part("tipoJuguete") tipoJuguete: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part image: MultipartBody.Part?
    ): Juguete

    // --- NUEVO: Obtener un solo juguete por ID ---
    @GET("juguetes/{id}")
    suspend fun getJugueteById(@Path("id") id: Int): Juguete

    // --- NUEVO: Actualizar un juguete (también Multipart) ---
    @Multipart
    @PUT("juguetes/{id}") // Usamos PUT para actualizar
    suspend fun updateJuguete(
        @Path("id") id: Int,
        @Part("nombre") nombre: RequestBody,
        @Part("tipoJuguete") tipoJuguete: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part image: MultipartBody.Part? // Permite enviar una nueva imagen o no
    ): Juguete

    // --- NUEVO: Eliminar un juguete ---
    @DELETE("juguetes/{id}")
    suspend fun deleteJuguete(@Path("id") id: Int): Response<Unit> // No esperamos contenido de vuelta
    @POST("register")
    suspend fun registerUser(@Body user: User): User
    @POST("login")
    suspend fun loginUser(@Body user: User): Response<Any>
}