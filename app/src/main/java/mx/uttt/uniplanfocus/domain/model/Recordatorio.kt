
package mx.uttt.uniplanfocus.domain.model

data class Recordatorio(
    val id: Int = 0,
    val actividadId: Int = 0,
    val mensaje: String = "",
    val fechaHora: Long = 0,
    val activo: Boolean = true
)
