package mx.uttt.uniplanfocus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Insert
    suspend fun insertarCategoria(categoria: CategoriaEntity)

    @Update
    suspend fun actualizarCategoria(categoria: CategoriaEntity)

    @Delete
    suspend fun eliminarCategoria(categoria: CategoriaEntity)

    @Query("SELECT * FROM categorias")
    fun obtenerCategorias(): Flow<List<CategoriaEntity>>
}