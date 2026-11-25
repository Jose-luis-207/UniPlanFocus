
package mx.uttt.uniplanfocus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordatorioDao {

    @Insert
    suspend fun insertarRecordatorio(recordatorio: RecordatorioEntity)

    @Query("SELECT * FROM recordatorios")
    fun obtenerRecordatorios(): Flow<List<RecordatorioEntity>>

    @Update
    suspend fun actualizarRecordatorio(recordatorio: RecordatorioEntity)

    @Delete
    suspend fun eliminarRecordatorio(recordatorio: RecordatorioEntity)
}
