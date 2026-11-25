package mx.uttt.uniplanfocus.data.repository

import mx.uttt.uniplanfocus.data.database.ActividadDao
import mx.uttt.uniplanfocus.data.mapper.toDomain
import mx.uttt.uniplanfocus.data.mapper.toEntity
import mx.uttt.uniplanfocus.domain.model.Actividad
import mx.uttt.uniplanfocus.domain.repository.ActividadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActividadRepositoryImp @Inject constructor(
    private val actividadDao: ActividadDao
) : ActividadRepository {

    override fun obtenerActividades(): Flow<List<Actividad>> {
        return actividadDao.obtenerActividades().map { lista ->
            lista.map { it.toDomain() }
        }
    }

    override fun obtenerActividadesPorCategoria(
        idCategoria: Int
    ): Flow<List<Actividad>> {
        return actividadDao.obtenerActividadesPorCategoria(idCategoria).map { lista ->
            lista.map { it.toDomain() }
        }
    }

    override suspend fun insertarActividad(actividad: Actividad) {
        actividadDao.insertarActividad(actividad.toEntity())
    }

    override suspend fun actualizarActividad(actividad: Actividad) {
        actividadDao.actualizarActividad(actividad.toEntity())
    }

    override suspend fun eliminarActividad(actividad: Actividad) {
        actividadDao.eliminarActividad(actividad.toEntity())
    }
}
//
//: Implementan los repositorios y conectan los modelos con los DAO.