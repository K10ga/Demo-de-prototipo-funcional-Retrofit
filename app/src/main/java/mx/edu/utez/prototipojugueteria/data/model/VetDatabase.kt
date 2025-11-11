package mx.edu.utez.prototipojugueteria.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Juguete::class], version = 1, exportSchema = false)
abstract class JugueteDataBase : RoomDatabase() {
    abstract fun jugueteDao(): JugueteDao

    companion object {
        @Volatile private var INSTANCE: JugueteDataBase? = null

        fun get(context: Context): JugueteDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JugueteDataBase::class.java,
                    "jugueteria.db" // Nombre de tu base de datos
                ).build().also { INSTANCE = it }
            }
    }
}