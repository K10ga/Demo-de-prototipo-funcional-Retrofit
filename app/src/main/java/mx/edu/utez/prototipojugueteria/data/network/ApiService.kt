package mx.edu.utez.prototipojugueteria.data.network

import mx.edu.utez.prototipojugueteria.data.model.Juguete
import mx.edu.utez.prototipojugueteria.data.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("juguetes")
    suspend fun getJuguetes(): List<Juguete>

    @Multipart
    @POST("juguetes")
    suspend fun addJuguete(
        @Part("nombre") nombre: RequestBody,
        @Part("tipoJuguete") tipoJuguete: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part images: List<MultipartBody.Part> // <-- AHORA ES UNA LISTA
    ): Juguete

    @GET("juguetes/{id}")
    suspend fun getJugueteById(@Path("id") id: Int): Juguete

    @Multipart
    @PUT("juguetes/{id}")
    suspend fun updateJuguete(
        @Path("id") id: Int,
        @Part("nombre") nombre: RequestBody,
        @Part("tipoJuguete") tipoJuguete: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part images: List<MultipartBody.Part> // <-- AHORA ES UNA LISTA
    ): Juguete

    @DELETE("juguetes/{id}")
    suspend fun deleteJuguete(@Path("id") id: Int): Response<Unit>

    // --- NUEVO: ENDPOINT PARA COMPRAR ---
    @POST("juguetes/{id}/comprar")
    suspend fun comprarJuguete(
        @Path("id") id: Int,
        @Body body: Map<String, Int>
    ): Juguete

    @POST("register")
    suspend fun registerUser(@Body user: User): User
    @POST("login")
    suspend fun loginUser(@Body user: User): Response<Any>
}