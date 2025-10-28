package mx.edu.utez.prototipojugueteria.data.model

import androidx.annotation.DrawableRes

// Definición del modelo de datos para un Juguete
// (Corregí la sintaxis de tu PDF para que sea un data class)
data class Juguete(
    val id: Int,
    val nombreJuguete: String, //
    val tipoJuguete: String, //
    val precio: Int, //
    @DrawableRes val imagen: Int //
)