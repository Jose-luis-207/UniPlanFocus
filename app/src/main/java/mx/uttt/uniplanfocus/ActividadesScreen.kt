
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uttt.uniplanfocus.domain.model.Categoria
import mx.uttt.uniplanfocus.domain.model.Actividad
import mx.uttt.uniplanfocus.ui.theme.*
import mx.uttt.uniplanfocus.ui.viewmodel.CategoriaViewModel
import mx.uttt.uniplanfocus.ui.viewmodel.ActividadViewModel

@Composable
fun ActividadesScreen(
    navController: NavHostController,
    categoriaViewModel: CategoriaViewModel = hiltViewModel(),
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {

    // OBSERVAR LOS DATOS
    val categorias by categoriaViewModel.categorias.collectAsStateWithLifecycle()
    val actividades by actividadViewModel.actividades.collectAsStateWithLifecycle()

    // OBSERVAR ERROR AL ELIMINAR CATEGORIA
    val errorEliminar by categoriaViewModel.errorEliminar.collectAsStateWithLifecycle()

    val errorCamposVacios = stringResource(R.string.error_campos_vacios)
    val mensajeCategoriaRelacionada =
        stringResource(R.string.error_categoria_relacionada)

    var mensajeError by remember { mutableStateOf("") }

    // VARIABLES DE CATEGORIAS
    var nombreCategoria by remember { mutableStateOf("") }

    var categoriaEditando by remember {
        mutableStateOf<Categoria?>(null)
    }

    // VARIABLES DE ACTIVIDADES
    var nombreActividad by remember { mutableStateOf("") }
    var descripcionActividad by remember { mutableStateOf("") }

    var categoriaSeleccionada by remember {
        mutableStateOf<Categoria?>(null)
    }

    var actividadEditando by remember {
        mutableStateOf<Actividad?>(null)
    }

    var menuAbierto by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = LavenderBackground
    ) { espacio ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(R.string.titulo_actividades),
                color = PurplePrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.descripcion_actividades),
                color = TextSecondary,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            // REGISTRAR O EDITAR CATEGORIAS
            Text(
                text = stringResource(R.string.registrar_categoria),
                color = TextPrimary,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = nombreCategoria,
                onValueChange = {
                    nombreCategoria = it
                    mensajeError = ""
                    categoriaViewModel.limpiarErrorEliminar()
                },
                label = {
                    Text(stringResource(R.string.nombre_categoria))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (nombreCategoria.isNotBlank()) {

                        if (categoriaEditando == null) {
                            categoriaViewModel.insertarCategoria(
                                Categoria(
                                    nombre = nombreCategoria.trim()
                                )
                            )
                        } else {
                            categoriaViewModel.actualizarCategoria(
                                categoriaEditando!!.copy(
                                    nombre = nombreCategoria.trim()
                                )
                            )
                        }

                        nombreCategoria = ""
                        categoriaEditando = null
                        mensajeError = ""

                    } else {
                        mensajeError = errorCamposVacios
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurplePrimary
                )
            ) {
                if (categoriaEditando == null) {
                    Text(stringResource(R.string.guardar_categoria))
                } else {
                    Text(stringResource(R.string.actualizar_categoria))
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // MOSTRAR CATEGORIAS
            Text(
                text = stringResource(R.string.mis_categorias),
                color = TextPrimary,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (errorEliminar) {
                Text(
                    text = mensajeCategoriaRelacionada,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            if (categorias.isEmpty()) {
                Text(
                    text = stringResource(R.string.sin_categorias),
                    color = TextSecondary
                )
            } else {
                categorias.forEach { categoria ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = categoria.nombre,
                                color = TextPrimary,
                                fontSize = 17.sp
                            )

                            Row {
                                TextButton(
                                    onClick = {
                                        categoriaEditando = categoria
                                        nombreCategoria = categoria.nombre
                                        mensajeError = ""
                                        categoriaViewModel.limpiarErrorEliminar()
                                    }
                                ) {
                                    Text(stringResource(R.string.editar_categoria))
                                }

                                TextButton(
                                    onClick = {
                                        categoriaViewModel.eliminarCategoria(categoria)

                                        if (categoriaEditando?.id == categoria.id) {
                                            categoriaEditando = null
                                            nombreCategoria = ""
                                        }
                                    }
                                ) {
                                    Text(stringResource(R.string.eliminar))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // REGISTRAR O EDITAR ACTIVIDADES
            Text(
                text = stringResource(R.string.registrar_actividad),
                color = TextPrimary,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = nombreActividad,
                onValueChange = {
                    nombreActividad = it
                    mensajeError = ""
                },
                label = {
                    Text(stringResource(R.string.nombre_actividad))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcionActividad,
                onValueChange = {
                    descripcionActividad = it
                    mensajeError = ""
                },
                label = {
                    Text(stringResource(R.string.descripcion))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // SELECCIONAR CATEGORIA
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        menuAbierto = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = categoriaSeleccionada?.nombre
                            ?: stringResource(R.string.seleccionar_categoria)
                    )
                }

                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = {
                        menuAbierto = false
                    }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(categoria.nombre)
                            },
                            onClick = {
                                categoriaSeleccionada = categoria
                                menuAbierto = false
                                mensajeError = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // MOSTRAR ERROR DE VALIDACION
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // GUARDAR O ACTUALIZAR ACTIVIDAD
            Button(
                onClick = {
                    if (
                        nombreActividad.isNotBlank() &&
                        descripcionActividad.isNotBlank() &&
                        categoriaSeleccionada != null
                    ) {

                        if (actividadEditando == null) {
                            actividadViewModel.insertarActividad(
                                Actividad(
                                    nombre = nombreActividad.trim(),
                                    descripcion = descripcionActividad.trim(),
                                    categoriaId = categoriaSeleccionada!!.id
                                )
                            )
                        } else {
                            actividadViewModel.actualizarActividad(
                                actividadEditando!!.copy(
                                    nombre = nombreActividad.trim(),
                                    descripcion = descripcionActividad.trim(),
                                    categoriaId = categoriaSeleccionada!!.id
                                )
                            )
                        }

                        nombreActividad = ""
                        descripcionActividad = ""
                        categoriaSeleccionada = null
                        actividadEditando = null
                        mensajeError = ""

                    } else {
                        mensajeError = errorCamposVacios
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurplePrimary
                )
            ) {
                if (actividadEditando == null) {
                    Text(stringResource(R.string.guardar_actividad))
                } else {
                    Text(stringResource(R.string.actualizar_actividad))
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // MOSTRAR ACTIVIDADES
            Text(
                text = stringResource(R.string.mis_actividades_registradas),
                color = TextPrimary,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            if (actividades.isEmpty()) {
                Text(
                    text = stringResource(R.string.sin_actividades),
                    color = TextSecondary
                )
            } else {
                actividades.forEach { actividad ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = actividad.nombre,
                                color = TextPrimary,
                                fontSize = 18.sp
                            )

                            Text(
                                text = actividad.descripcion,
                                color = TextSecondary
                            )

                            val nombreDeCategoria = categorias.find {
                                it.id == actividad.categoriaId
                            }?.nombre ?: stringResource(R.string.sin_categoria)

                            Text(
                                text = stringResource(
                                    R.string.categoria_texto,
                                    nombreDeCategoria
                                ),
                                color = PurplePrimary
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = {
                                        actividadEditando = actividad
                                        nombreActividad = actividad.nombre
                                        descripcionActividad = actividad.descripcion

                                        categoriaSeleccionada = categorias.find {
                                            it.id == actividad.categoriaId
                                        }

                                        mensajeError = ""
                                    }
                                ) {
                                    Text(stringResource(R.string.editar_actividad))
                                }

                                TextButton(
                                    onClick = {
                                        actividadViewModel.eliminarActividad(actividad)
                                    }
                                ) {
                                    Text(stringResource(R.string.eliminar_actividad))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.regresar_inicio))
            }
        }
    }
}

// VISTA PREVIA DE ACTIVIDADES
@Preview(showBackground = true)
@Composable
fun ActividadesScreenPreview() {
    UniPlanFocusTheme {
        Scaffold(
            containerColor = LavenderBackground
        ) { espacio ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(espacio)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = stringResource(R.string.titulo_actividades),
                    color = PurplePrimary,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.descripcion_actividades),
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(R.string.mis_categorias),
                    color = TextPrimary,
                    fontSize = 20.sp
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.nombre_categoria),
                        modifier = Modifier.padding(16.dp),
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(R.string.mis_actividades_registradas),
                    color = TextPrimary,
                    fontSize = 20.sp
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.nombre_actividad),
                            color = TextPrimary,
                            fontSize = 18.sp
                        )

                        Text(
                            text = stringResource(R.string.descripcion),
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}
