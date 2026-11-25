
package mx.uttt.uniplanfocus

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import mx.uttt.uniplanfocus.domain.model.Recordatorio
import mx.uttt.uniplanfocus.ui.viewmodel.ActividadViewModel
import mx.uttt.uniplanfocus.ui.viewmodel.RecordatorioViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun RecordatoriosScreen(
    navController: NavHostController,
    recordatorioViewModel: RecordatorioViewModel = hiltViewModel(),
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {

    val contexto = LocalContext.current

    val recordatorios by recordatorioViewModel.recordatorios.collectAsState()
    val actividades by actividadViewModel.actividades.collectAsState()

    var actividadId by remember { mutableIntStateOf(0) }
    var mensaje by remember { mutableStateOf("") }
    var fechaHora by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    var menuActividades by remember { mutableStateOf(false) }
    var recordatorioEditando by remember {
        mutableStateOf<Recordatorio?>(null)
    }
    var mensajeError by remember { mutableStateOf("") }

    val actividadSeleccionada = actividades.find {
        it.id == actividadId
    }

    val formatoFecha = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { espacio ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text(stringResource(R.string.regresar))
            }

            Text(
                text = stringResource(R.string.titulo_recordatorios),
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = stringResource(R.string.descripcion_recordatorios),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                stringResource(R.string.seleccionar_actividad_recordatorio)
            )

            Box {

                OutlinedButton(
                    onClick = { menuActividades = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        actividadSeleccionada?.nombre
                            ?: stringResource(
                                R.string.seleccionar_actividad_recordatorio
                            )
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

            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = {
                    Text(stringResource(R.string.mensaje_recordatorio))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {

                    val calendario = Calendar.getInstance()
                    calendario.timeInMillis = fechaHora

                    DatePickerDialog(
                        contexto,
                        { _, anio, mes, dia ->

                            calendario.set(
                                anio,
                                mes,
                                dia
                            )

                            fechaHora = calendario.timeInMillis
                        },
                        calendario.get(Calendar.YEAR),
                        calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "${stringResource(R.string.fecha_recordatorio)}: " +
                            formatoFecha.format(fechaHora)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {

                    val calendario = Calendar.getInstance()
                    calendario.timeInMillis = fechaHora

                    TimePickerDialog(
                        contexto,
                        { _, hora, minuto ->

                            calendario.set(
                                Calendar.HOUR_OF_DAY,
                                hora
                            )

                            calendario.set(
                                Calendar.MINUTE,
                                minuto
                            )

                            fechaHora = calendario.timeInMillis
                        },
                        calendario.get(Calendar.HOUR_OF_DAY),
                        calendario.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.hora_recordatorio))
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {

                    if (actividadId == 0 || mensaje.isBlank()) {

                        mensajeError = contexto.getString(
                            R.string.error_campos_vacios
                        )

                    } else {

                        val recordatorio = Recordatorio(
                            id = recordatorioEditando?.id ?: 0,
                            actividadId = actividadId,
                            mensaje = mensaje,
                            fechaHora = fechaHora,
                            activo = recordatorioEditando?.activo ?: true
                        )

                        if (recordatorioEditando == null) {
                            recordatorioViewModel.insertarRecordatorio(
                                recordatorio
                            )
                        } else {
                            recordatorioViewModel.actualizarRecordatorio(
                                recordatorio
                            )
                        }

                        recordatorioEditando = null
                        actividadId = 0
                        mensaje = ""
                        fechaHora = System.currentTimeMillis()
                        mensajeError = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (recordatorioEditando == null) {
                        stringResource(R.string.guardar_recordatorio)
                    } else {
                        stringResource(R.string.actualizar_recordatorio)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.lista_recordatorios),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (recordatorios.isEmpty()) {
                Text(stringResource(R.string.sin_recordatorios))
            }

            recordatorios.forEach { recordatorio ->

                val nombreActividad = actividades.find {
                    it.id == recordatorio.actividadId
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

                        Text(text = recordatorio.mensaje)

                        Text(
                            text = formatoFecha.format(
                                recordatorio.fechaHora
                            )
                        )

                        Text(
                            text = stringResource(
                                if (recordatorio.activo) {
                                    R.string.recordatorio_activo
                                } else {
                                    R.string.recordatorio_inactivo
                                }
                            )
                        )

                        Row {

                            TextButton(
                                onClick = {

                                    recordatorioEditando = recordatorio
                                    actividadId = recordatorio.actividadId
                                    mensaje = recordatorio.mensaje
                                    fechaHora = recordatorio.fechaHora
                                    mensajeError = ""
                                }
                            ) {
                                Text(stringResource(R.string.editar))
                            }

                            TextButton(
                                onClick = {

                                    recordatorioViewModel.eliminarRecordatorio(
                                        recordatorio
                                    )
                                }
                            ) {
                                Text(stringResource(R.string.eliminar))
                            }

                            TextButton(
                                onClick = {

                                    recordatorioViewModel.actualizarRecordatorio(
                                        recordatorio.copy(
                                            activo = !recordatorio.activo
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    stringResource(
                                        if (recordatorio.activo) {
                                            R.string.recordatorio_inactivo
                                        } else {
                                            R.string.recordatorio_activo
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
