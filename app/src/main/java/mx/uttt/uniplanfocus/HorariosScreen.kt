
package mx.uttt.uniplanfocus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import mx.uttt.uniplanfocus.domain.model.Horario
import mx.uttt.uniplanfocus.ui.viewmodel.ActividadViewModel
import mx.uttt.uniplanfocus.ui.viewmodel.HorarioViewModel
import mx.uttt.uniplanfocus.ui.theme.UniPlanFocusTheme

@Composable
fun HorariosScreen(
    navController: NavHostController,
    horarioViewModel: HorarioViewModel = hiltViewModel(),
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {

    val horarios by horarioViewModel.horarios.collectAsStateWithLifecycle()
    val actividades by actividadViewModel.actividades.collectAsStateWithLifecycle()

    // TEXTOS PARA VALIDACIONES Y MENSAJES
    val errorSinActividades = stringResource(R.string.error_sin_actividades)
    val errorCamposVacios = stringResource(R.string.error_campos_vacios)
    val errorHora = stringResource(R.string.error_hora)
    val errorRangoHora = stringResource(R.string.error_rango_hora)
    val horarioGuardado = stringResource(R.string.horario_guardado)
    val horarioActualizado = stringResource(R.string.horario_actualizado)

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

    var horarioEditando by remember {
        mutableStateOf<Horario?>(null)
    }

    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    val contextoActividad = actividades.find {
        it.id == actividadId
    }

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
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(stringResource(R.string.regresar))
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

            // SELECCIONAR ACTIVIDAD
            Text(stringResource(R.string.seleccionar_actividad))

            Box {
                OutlinedButton(
                    onClick = {
                        if (actividades.isNotEmpty()) {
                            menuActividades = true
                        } else {
                            mensajeError = errorSinActividades
                            mensajeExito = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        contextoActividad?.nombre
                            ?: stringResource(R.string.seleccionar_actividad)
                    )
                }

                DropdownMenu(
                    expanded = menuActividades,
                    onDismissRequest = {
                        menuActividades = false
                    }
                ) {
                    actividades.forEach { actividad ->
                        DropdownMenuItem(
                            text = {
                                Text(actividad.nombre)
                            },
                            onClick = {
                                actividadId = actividad.id
                                menuActividades = false
                                mensajeError = ""
                                mensajeExito = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // SELECCIONAR DIA
            Text(stringResource(R.string.dia_semana))

            Box {
                OutlinedButton(
                    onClick = {
                        menuDias = true
                    },
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
                    onDismissRequest = {
                        menuDias = false
                    }
                ) {
                    dias.forEach { dia ->
                        DropdownMenuItem(
                            text = {
                                Text(dia)
                            },
                            onClick = {
                                diaSeleccionado = dia
                                menuDias = false
                                mensajeError = ""
                                mensajeExito = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // HORA DE INICIO
            OutlinedTextField(
                value = horaInicio,
                onValueChange = {
                    horaInicio = it
                    mensajeError = ""
                    mensajeExito = ""
                },
                label = {
                    Text(stringResource(R.string.hora_inicio))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // HORA DE FIN
            OutlinedTextField(
                value = horaFin,
                onValueChange = {
                    horaFin = it
                    mensajeError = ""
                    mensajeExito = ""
                },
                label = {
                    Text(stringResource(R.string.hora_fin))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // MOSTRAR MENSAJES
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (mensajeExito.isNotEmpty()) {
                Text(
                    text = mensajeExito,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // GUARDAR O ACTUALIZAR HORARIO
            Button(
                onClick = {

                    val formatoHora = Regex(
                        "([01]\\d|2[0-3]):[0-5]\\d"
                    )

                    when {

                        actividadId == 0 ||
                                diaSeleccionado.isEmpty() ||
                                horaInicio.isBlank() ||
                                horaFin.isBlank() -> {

                            mensajeError = errorCamposVacios
                            mensajeExito = ""
                        }

                        !formatoHora.matches(horaInicio) ||
                                !formatoHora.matches(horaFin) -> {

                            mensajeError = errorHora
                            mensajeExito = ""
                        }

                        horaFin <= horaInicio -> {

                            mensajeError = errorRangoHora
                            mensajeExito = ""
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
                                mensajeExito = horarioGuardado

                            } else {

                                horarioViewModel.actualizarHorario(horario)
                                mensajeExito = horarioActualizado
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

            // LISTA DE HORARIOS
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

                            // EDITAR
                            TextButton(
                                onClick = {
                                    horarioEditando = horario
                                    actividadId = horario.actividadId
                                    diaSeleccionado = horario.diaSemana
                                    horaInicio = horario.horaInicio
                                    horaFin = horario.horaFin

                                    mensajeError = ""
                                    mensajeExito = ""
                                }
                            ) {
                                Text(stringResource(R.string.editar))
                            }

                            // ELIMINAR
                            TextButton(
                                onClick = {
                                    horarioViewModel.eliminarHorario(horario)

                                    if (horarioEditando?.id == horario.id) {
                                        horarioEditando = null
                                        actividadId = 0
                                        diaSeleccionado = ""
                                        horaInicio = ""
                                        horaFin = ""
                                    }

                                    mensajeError = ""
                                    mensajeExito = ""
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

// VISTA PREVIA
@Preview(showBackground = true)
@Composable
fun HorariosScreenPreview() {
    UniPlanFocusTheme {
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

                Text(
                    text = stringResource(R.string.regresar)
                )

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

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.seleccionar_actividad))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(stringResource(R.string.dia_semana))

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.dia_semana))
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(stringResource(R.string.hora_inicio))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(stringResource(R.string.hora_fin))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.guardar_horario))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.lista_horarios),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(stringResource(R.string.nombre_actividad))
                        Text(stringResource(R.string.lunes))
                        Text("08:00 - 09:00")

                        Row {
                            TextButton(onClick = {}) {
                                Text(stringResource(R.string.editar))
                            }

                            TextButton(onClick = {}) {
                                Text(stringResource(R.string.eliminar))
                            }
                        }
                    }
                }
            }
        }
    }
}
