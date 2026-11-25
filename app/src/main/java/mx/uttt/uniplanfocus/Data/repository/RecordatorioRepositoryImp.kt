
package mx.uttt.uniplanfocus.data.repository

import mx.uttt.uniplanfocus.data.database.RecordatorioDao
import mx.uttt.uniplanfocus.data.mapper.toDomain
import mx.uttt.uniplanfocus.data.mapper.toEntity
import mx.uttt.uniplanfocus.domain.model.Recordatorio
import mx.uttt.uniplanfocus.domain.repository.RecordatorioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordatorioRepositoryImp @Inject constructor(
    private val recordatorioDao: RecordatorioDao
) : RecordatorioRepository {

    override fun obtenerRecordatorios(): Flow<List<Recordatorio>> =
        recordatorioDao.obtenerRecordatorios().map { lista ->
            lista.map { it.toDomain() }
        }

    override suspend fun insertarRecordatorio(recordatorio: Recordatorio) {
        recordatorioDao.insertarRecordatorio(recordatorio.toEntity())
    }

    override suspend fun actualizarRecordatorio(recordatorio: Recordatorio) {
        recordatorioDao.actualizarRecordatorio(recordatorio.toEntity())
    }

    override suspend fun eliminarRecordatorio(recordatorio: Recordatorio) {
        recordatorioDao.eliminarRecordatorio(recordatorio.toEntity())
    }
}
