package mx.uttt.uniplanfocus.domain.repository

import mx.uttt.uniplanfocus.domain.model.Categoria
import kotlinx.coroutines.flow.Flow

interface CategoriaRepository {

    fun obtenerCategorias(): Flow<List<Categoria>>

    suspend fun insertarCategoria(categoria: Categoria)

    suspend fun actualizarCategoria(categoria: Categoria)

    suspend fun eliminarCategoria(categoria: Categoria)
}