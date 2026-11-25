package mx.uttt.uniplanfocus.domain.repository

import kotlinx.coroutines.flow.Flow
import mx.uttt.uniplanfocus.domain.model.Recordatorio

interface RecordatorioRepository {

    fun obtenerRecordatorios(): Flow<List<Recordatorio>>

    suspend fun insertarRecordatorio(recordatorio: Recordatorio)

    suspend fun actualizarRecordatorio(recordatorio: Recordatorio)

    suspend fun eliminarRecordatorio(recordatorio: Recordatorio)
}