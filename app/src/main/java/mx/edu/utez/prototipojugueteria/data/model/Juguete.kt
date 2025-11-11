package mx.edu.utez.prototipojugueteria.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "juguetes")
data class Juguete(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @DrawableRes val image: Int,
    val nombre: String,
    val tipoJuguete: String?,
    val precio: Double
)