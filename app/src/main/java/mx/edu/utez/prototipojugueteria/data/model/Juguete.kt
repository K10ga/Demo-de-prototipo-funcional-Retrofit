package mx.edu.utez.prototipojugueteria.data.model

// Ya no importamos Room
// import androidx.room.Entity
// import androidx.room.PrimaryKey
// import androidx.annotation.DrawableRes

/**
 * Esta es la nueva data class.
 * Se eliminaron las anotaciones de Room (@Entity, @PrimaryKey).
 * 'image' ahora es 'imageUrl' (String?) para recibir una URL de la API,
 * igual que en tu clase Pet.
 *
 * Mantuve 'nombre', 'tipoJuguete' y 'precio' como pediste.
 */
data class Juguete(
    val id: Int = 0, // O Long, dependiendo de tu nueva API
    val nombre: String,
    val tipoJuguete: String?,
    val precio: Double,
    val imageUrl: String? // Antes era '@DrawableRes val image: Int'
)