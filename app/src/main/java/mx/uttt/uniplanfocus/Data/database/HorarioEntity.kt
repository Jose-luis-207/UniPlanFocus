package mx.uttt.uniplanfocus.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "horarios",
    foreignKeys = [
        ForeignKey(
            entity = ActividadEntity::class,
            parentColumns = ["id"],
            childColumns = ["actividadId"]
        )
    ]
)
data class HorarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val actividadId: Int,
    val diaSemana: String,
    val horaInicio: String,
    val horaFin: String
)