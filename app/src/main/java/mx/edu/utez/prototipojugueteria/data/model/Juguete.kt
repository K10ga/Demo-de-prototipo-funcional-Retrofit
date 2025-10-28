package mx.edu.utez.prototipojugueteria.data.model

import androidx.annotation.DrawableRes

data class Juguete(
    val id: Int,
    val nombreJuguete: String, //
    val tipoJuguete: String, //
    val precio: Int, //
    @DrawableRes val imagen: Int //
)