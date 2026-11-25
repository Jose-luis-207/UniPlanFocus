
package mx.uttt.uniplanfocus.data.mapper

import mx.uttt.uniplanfocus.data.database.RecordatorioEntity
import mx.uttt.uniplanfocus.domain.model.Recordatorio

fun RecordatorioEntity.toDomain(): Recordatorio {
    return Recordatorio(
        id = id,
        actividadId = actividadId,
        mensaje = mensaje,
        fechaHora = fechaHora,
        activo = activo
    )
}

fun Recordatorio.toEntity(): RecordatorioEntity {
    return RecordatorioEntity(
        id = id,
        actividadId = actividadId,
        mensaje = mensaje,
        fechaHora = fechaHora,
        activo = activo
    )
}
