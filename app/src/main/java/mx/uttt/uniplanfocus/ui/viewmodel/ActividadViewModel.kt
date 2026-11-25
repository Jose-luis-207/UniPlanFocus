package mx.uttt.uniplanfocus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uttt.uniplanfocus.domain.model.Actividad
import mx.uttt.uniplanfocus.domain.repository.ActividadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActividadViewModel @Inject constructor(
    private val repository: ActividadRepository
) : ViewModel() {

    val actividades = repository.obtenerActividades()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertarActividad(actividad: Actividad) {
        viewModelScope.launch {
            repository.insertarActividad(actividad)
        }
    }

    fun actualizarActividad(actividad: Actividad) {
        viewModelScope.launch {
            repository.actualizarActividad(actividad)
        }
    }

    fun eliminarActividad(actividad: Actividad) {
        viewModelScope.launch {
            repository.eliminarActividad(actividad)
        }
    }

    fun obtenerActividadesPorCategoria(idCategoria: Int) {
        viewModelScope.launch {
            repository.obtenerActividadesPorCategoria(idCategoria)
        }
    }
}