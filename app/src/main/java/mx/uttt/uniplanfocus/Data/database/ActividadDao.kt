package mx.uttt.uniplanfocus.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Insert
    suspend fun insertarActividad(actividad: ActividadEntity)

    @Update
    suspend fun actualizarActividad(actividad: ActividadEntity)

    @Delete
    suspend fun eliminarActividad(actividad: ActividadEntity)

    @Query("SELECT * FROM actividades")
    fun obtenerActividades(): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE categoriaId = :idCategoria")
    fun obtenerActividadesPorCategoria(idCategoria: Int): Flow<List<ActividadEntity>>
}
//Contienen métodos para insertar,
// actualizar, eliminar y consultar registros. Las consultas de listas utilizan Flow