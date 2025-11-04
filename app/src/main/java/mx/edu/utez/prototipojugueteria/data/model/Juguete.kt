package mx.edu.utez.prototipojugueteria.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "juguetes")
data class Juguete(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val nombreJuguete: String, //
    val tipoJuguete: String, //
    val precio: Float, //
    @DrawableRes val imagen: Int //
)