
package mx.uttt.uniplanfocus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface HorarioDao {

    @Insert
    suspend fun insertarHorario(horario: HorarioEntity)

    @Query("SELECT * FROM horarios")
    fun obtenerHorarios(): Flow<List<HorarioEntity>>

    @Update
    suspend fun actualizarHorario(horario: HorarioEntity)

    @Delete
    suspend fun eliminarHorario(horario: HorarioEntity)
}
