
package mx.uttt.uniplanfocus.data.mapper

import mx.uttt.uniplanfocus.data.database.HorarioEntity
import mx.uttt.uniplanfocus.domain.model.Horario

fun HorarioEntity.toDomain(): Horario {
    return Horario(
        id = id,
        actividadId = actividadId,
        diaSemana = diaSemana,
        horaInicio = horaInicio,
        horaFin = horaFin
    )
}

fun Horario.toEntity(): HorarioEntity {
    return HorarioEntity(
        id = id,
        actividadId = actividadId,
        diaSemana = diaSemana,
        horaInicio = horaInicio,
        horaFin = horaFin
    )
}
