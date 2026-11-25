
package mx.uttt.uniplanfocus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uttt.uniplanfocus.domain.model.Categoria
import mx.uttt.uniplanfocus.domain.repository.CategoriaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val repository: CategoriaRepository
) : ViewModel() {

    val categorias = repository.obtenerCategorias()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _errorEliminar = MutableStateFlow(false)
    val errorEliminar = _errorEliminar.asStateFlow()

    fun insertarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            repository.insertarCategoria(categoria)
        }
    }

    fun actualizarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            repository.actualizarCategoria(categoria)
        }
    }

    fun eliminarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            try {
                repository.eliminarCategoria(categoria)
                _errorEliminar.value = false
            } catch (e: Exception) {
                _errorEliminar.value = true
            }
        }
    }

    fun limpiarErrorEliminar() {
        _errorEliminar.value = false
    }
}
