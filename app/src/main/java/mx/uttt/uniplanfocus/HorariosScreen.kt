
package mx.uttt.uniplanfocus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import mx.uttt.uniplanfocus.domain.model.Horario
import mx.uttt.uniplanfocus.ui.viewmodel.ActividadViewModel
import mx.uttt.uniplanfocus.ui.viewmodel.HorarioViewModel

@Composable
fun HorariosScreen(
    navController: NavHostController,
    horarioViewModel: HorarioViewModel = hiltViewModel(),
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {
    val horarios by horarioViewModel.horarios.collectAsStateWithLifecycle()
    val actividades by actividadViewModel.actividades.collectAsStateWithLifecycle()

    val dias = listOf(
        stringResource(R.string.lunes),
        stringResource(R.string.martes),
        stringResource(R.string.miercoles),
        stringResource(R.string.jueves),
        stringResource(R.string.viernes),
        stringResource(R.string.sabado),
        stringResource(R.string.domingo)
    )

    var actividadId by remember { mutableIntStateOf(0) }
    var diaSeleccionado by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }

    var menuActividades by remember { mutableStateOf(false) }
    var menuDias by remember { mutableStateOf(false) }
    var horarioEditando by remember { mutableStateOf<Horario?>(null) }
    var mensajeError by remember { mutableStateOf("") }

    val contextoActividad = actividades.find { it.id == actividadId }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("← Regresar")
            }

            Text(
                text = stringResource(R.string.titulo_horarios),
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = stringResource(R.string.descripcion_horarios),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(stringResource(R.string.seleccionar_actividad))

            Box {
                OutlinedButton(
                    onClick = { menuActividades = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        contextoActividad?.nombre
                            ?: stringResource(R.string.seleccionar_actividad)
                    )
                }

                DropdownMenu(
                    expanded = menuActividades,
                    onDismissRequest = { menuActividades = false }
                ) {
                    actividades.forEach { actividad ->
                        DropdownMenuItem(
                            text = { Text(actividad.nombre) },
                            onClick = {
                                actividadId = actividad.id
                                menuActividades = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(stringResource(R.string.dia_semana))

            Box {
                OutlinedButton(
                    onClick = { menuDias = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (diaSeleccionado.isEmpty()) {
                            stringResource(R.string.dia_semana)
                        } else {
                            diaSeleccionado
                        }
                    )
                }

                DropdownMenu(
                    expanded = menuDias,
                    onDismissRequest = { menuDias = false }
                ) {
                    dias.forEach { dia ->
                        DropdownMenuItem(
                            text = { Text(dia) },
                            onClick = {
                                diaSeleccionado = dia
                                menuDias = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = horaInicio,
                onValueChange = { horaInicio = it },
                label = {
                    Text(stringResource(R.string.hora_inicio))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = horaFin,
                onValueChange = { horaFin = it },
                label = {
                    Text(stringResource(R.string.hora_fin))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    val formatoHora = Regex("([01]\\d|2[0-3]):[0-5]\\d")

                    when {
                        actividadId == 0 ||
                                diaSeleccionado.isEmpty() ||
                                horaInicio.isBlank() ||
                                horaFin.isBlank() -> {
                            mensajeError =
                                "Completa todos los campos."
                        }

                        !formatoHora.matches(horaInicio) ||
                                !formatoHora.matches(horaFin) -> {
                            mensajeError =
                                "Escribe las horas en formato HH:mm."
                        }

                        horaFin <= horaInicio -> {
                            mensajeError =
                                "La hora de fin debe ser posterior a la hora de inicio."
                        }

                        else -> {
                            val horario = Horario(
                                id = horarioEditando?.id ?: 0,
                                actividadId = actividadId,
                                diaSemana = diaSeleccionado,
                                horaInicio = horaInicio,
                                horaFin = horaFin
                            )

                            if (horarioEditando == null) {
                                horarioViewModel.insertarHorario(horario)
                            } else {
                                horarioViewModel.actualizarHorario(horario)
                            }

                            horarioEditando = null
                            actividadId = 0
                            diaSeleccionado = ""
                            horaInicio = ""
                            horaFin = ""
                            mensajeError = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (horarioEditando == null) {
                        stringResource(R.string.guardar_horario)
                    } else {
                        stringResource(R.string.actualizar_horario)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.lista_horarios),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (horarios.isEmpty()) {
                Text(stringResource(R.string.sin_horarios))
            }

            horarios.forEach { horario ->
                val nombreActividad = actividades.find {
                    it.id == horario.actividadId
                }?.nombre ?: ""

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = nombreActividad,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = horario.diaSemana)
                        Text(
                            text = "${horario.horaInicio} - ${horario.horaFin}"
                        )

                        Row {
                            TextButton(
                                onClick = {
                                    horarioEditando = horario
                                    actividadId = horario.actividadId
                                    diaSeleccionado = horario.diaSemana
                                    horaInicio = horario.horaInicio
                                    horaFin = horario.horaFin
                                    mensajeError = ""
                                }
                            ) {
                                Text(stringResource(R.string.editar))
                            }

                            TextButton(
                                onClick = {
                                    horarioViewModel.eliminarHorario(horario)
                                }
                            ) {
                                Text(stringResource(R.string.eliminar))
                            }
                        }
                    }
                }
            }
        }
    }
}
