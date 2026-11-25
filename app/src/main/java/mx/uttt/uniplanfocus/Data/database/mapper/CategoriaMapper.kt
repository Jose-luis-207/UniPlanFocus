package mx.uttt.uniplanfocus.data.mapper

import mx.uttt.uniplanfocus.data.database.CategoriaEntity
import mx.uttt.uniplanfocus.domain.model.Categoria

fun CategoriaEntity.toDomain(): Categoria {
    return Categoria(
        id = id,
        nombre = nombre
    )
}

fun Categoria.toEntity(): CategoriaEntity {
    return CategoriaEntity(
        id = id,
        nombre = nombre
    )
}
//Convierten los modelos de dominio a entidades Room y viceversa.