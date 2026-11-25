package mx.uttt.uniplanfocus.data.repository

import mx.uttt.uniplanfocus.data.database.CategoriaDao
import mx.uttt.uniplanfocus.data.mapper.toDomain
import mx.uttt.uniplanfocus.data.mapper.toEntity
import mx.uttt.uniplanfocus.domain.model.Categoria
import mx.uttt.uniplanfocus.domain.repository.CategoriaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriaRepositoryImp @Inject constructor(
    private val categoriaDao: CategoriaDao
) : CategoriaRepository {

    override fun obtenerCategorias(): Flow<List<Categoria>> {
        return categoriaDao.obtenerCategorias().map { lista ->
            lista.map { it.toDomain() }
        }
    }

    override suspend fun insertarCategoria(categoria: Categoria) {
        categoriaDao.insertarCategoria(categoria.toEntity())
    }

    override suspend fun actualizarCategoria(categoria: Categoria) {
        categoriaDao.actualizarCategoria(categoria.toEntity())
    }

    override suspend fun eliminarCategoria(categoria: Categoria) {
        categoriaDao.eliminarCategoria(categoria.toEntity())
    }
}
