package mx.edu.utez.prototipojugueteria.viewmodel

data class JugueteUiState(
    val id: Long = 0,
    val nombre: String = "",
    val tipoJuguete: String = "",
    val precio: String = "",
    val image: Int = 0
)