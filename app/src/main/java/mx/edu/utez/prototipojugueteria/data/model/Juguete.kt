package mx.edu.utez.prototipojugueteria.data.model

data class Juguete(
    val id: Int = 0,
    val nombre: String,
    val tipoJuguete: String?,
    val precio: Double,
    val imageUrl: String?, // Imagen principal para compatibilidad

    // --- NUEVOS CAMPOS NECESARIOS ---
    val imagenesUrls: List<String> = emptyList(), // Lista para el carrusel
    val vendedorNombre: String? = "Anónimo",
    val userId: Int? = null,
    val vendido: Boolean = false,
    val compradorNombre: String? = null
)