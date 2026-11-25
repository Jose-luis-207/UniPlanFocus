package mx.uttt.uniplanfocus.domain.repository

import kotlinx.coroutines.flow.Flow
import mx.uttt.uniplanfocus.domain.model.Horario

interface HorarioRepository {

    fun obtenerHorarios(): Flow<List<Horario>>

    suspend fun insertarHorario(horario: Horario)

    suspend fun actualizarHorario(horario: Horario)

    suspend fun eliminarHorario(horario: Horario)
}