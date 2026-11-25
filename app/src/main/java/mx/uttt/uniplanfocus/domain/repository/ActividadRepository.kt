package mx.uttt.uniplanfocus.domain.repository

import mx.uttt.uniplanfocus.domain.model.Actividad
import kotlinx.coroutines.flow.Flow

interface ActividadRepository {

    fun obtenerActividades(): Flow<List<Actividad>>

    fun obtenerActividadesPorCategoria(idCategoria: Int): Flow<List<Actividad>>

    suspend fun insertarActividad(actividad: Actividad)

    suspend fun actualizarActividad(actividad: Actividad)

    suspend fun eliminarActividad(actividad: Actividad)
}