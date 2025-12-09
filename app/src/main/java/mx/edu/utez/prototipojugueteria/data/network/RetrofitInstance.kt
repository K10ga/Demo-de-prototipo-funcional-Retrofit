package mx.edu.utez.prototipojugueteria.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private const val BASE_URL = "http://192.168.100.226:5000/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia pública de tu ApiService de JUGUETES
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}