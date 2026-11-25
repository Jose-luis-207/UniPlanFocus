
package mx.uttt.uniplanfocus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uttt.uniplanfocus.domain.model.Recordatorio
import mx.uttt.uniplanfocus.domain.repository.RecordatorioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordatorioViewModel @Inject constructor(
    private val repository: RecordatorioRepository
) : ViewModel() {

    val recordatorios = repository.obtenerRecordatorios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            repository.insertarRecordatorio(recordatorio)
        }
    }

    fun actualizarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            repository.actualizarRecordatorio(recordatorio)
        }
    }

    fun eliminarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            repository.eliminarRecordatorio(recordatorio)
        }
    }
}
