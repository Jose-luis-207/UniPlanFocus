
package mx.uttt.uniplanfocus.domain.model

data class Horario(
    val id: Int = 0,
    val actividadId: Int = 0,
    val diaSemana: String = "",
    val horaInicio: String = "",
    val horaFin: String = ""
)
