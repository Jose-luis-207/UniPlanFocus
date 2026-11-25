package mx.uttt.uniplanfocus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uttt.uniplanfocus.domain.model.Categoria
import mx.uttt.uniplanfocus.domain.repository.CategoriaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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
            repository.eliminarCategoria(categoria)
        }
    }
}
//Solicitan operaciones a los repositorios y exponen los datos para la interfaz.