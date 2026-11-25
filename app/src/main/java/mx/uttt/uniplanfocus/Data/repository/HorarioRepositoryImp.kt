
package mx.uttt.uniplanfocus.data.repository

import mx.uttt.uniplanfocus.data.database.HorarioDao
import mx.uttt.uniplanfocus.data.mapper.toDomain
import mx.uttt.uniplanfocus.data.mapper.toEntity
import mx.uttt.uniplanfocus.domain.model.Horario
import mx.uttt.uniplanfocus.domain.repository.HorarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HorarioRepositoryImp @Inject constructor(
    private val horarioDao: HorarioDao
) : HorarioRepository {

    override fun obtenerHorarios(): Flow<List<Horario>> =
        horarioDao.obtenerHorarios().map { lista ->
            lista.map { it.toDomain() }
        }

    override suspend fun insertarHorario(horario: Horario) {
        horarioDao.insertarHorario(horario.toEntity())
    }

    override suspend fun actualizarHorario(horario: Horario) {
        horarioDao.actualizarHorario(horario.toEntity())
    }

    override suspend fun eliminarHorario(horario: Horario) {
        horarioDao.eliminarHorario(horario.toEntity())
    }
}
