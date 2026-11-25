package mx.uttt.uniplanfocus.data.mapper

import mx.uttt.uniplanfocus.data.database.ActividadEntity
import mx.uttt.uniplanfocus.domain.model.Actividad

fun ActividadEntity.toDomain(): Actividad {
    return Actividad(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        categoriaId = categoriaId
    )
}

fun Actividad.toEntity(): ActividadEntity {
    return ActividadEntity(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        categoriaId = categoriaId
    )
}