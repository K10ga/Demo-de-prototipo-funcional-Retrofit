package mx.edu.utez.prototipojugueteria.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Juguete::class], version = 1)
abstract class VetDatabase : RoomDatabase() {
    abstract fun jugueteDao(): JugueteDao
}