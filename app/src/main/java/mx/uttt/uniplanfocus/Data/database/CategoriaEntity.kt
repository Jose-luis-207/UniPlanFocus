package mx.uttt.uniplanfocus.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String
)
//Definen la estructura de las dos tablas, sus campos y la relación.