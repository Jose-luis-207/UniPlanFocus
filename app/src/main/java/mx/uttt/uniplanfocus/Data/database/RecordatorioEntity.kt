package mx.uttt.uniplanfocus.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recordatorios",
    foreignKeys = [
        ForeignKey(
            entity = ActividadEntity::class,
            parentColumns = ["id"],
            childColumns = ["actividadId"]
        )
    ]
)
data class RecordatorioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val actividadId: Int,
    val mensaje: String,
    val fechaHora: Long,
    val activo: Boolean = true
)