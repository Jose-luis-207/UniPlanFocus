
package mx.uttt.uniplanfocus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uttt.uniplanfocus.domain.model.Horario
import mx.uttt.uniplanfocus.domain.repository.HorarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HorarioViewModel @Inject constructor(
    private val repository: HorarioRepository
) : ViewModel() {

    val horarios = repository.obtenerHorarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertarHorario(horario: Horario) {
        viewModelScope.launch {
            repository.insertarHorario(horario)
        }
    }

    fun actualizarHorario(horario: Horario) {
        viewModelScope.launch {
            repository.actualizarHorario(horario)
        }
    }

    fun eliminarHorario(horario: Horario) {
        viewModelScope.launch {
            repository.eliminarHorario(horario)
        }
    }
}
