
package mx.uttt.uniplanfocus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.uttt.uniplanfocus.ui.theme.*

@Composable
fun InicioScreen(navController: NavHostController) {

    Scaffold(
        containerColor = LavenderBackground
    ) { espacio ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(24.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.logo_uniplan),
                contentDescription = stringResource(R.string.descripcion_logo),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.titulo_inicio),
                color = PurplePrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.bienvenida),
                color = TextPrimary,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.frase_inicio),
                color = TextSecondary,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.actividades_hoy),
                color = TextPrimary,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.actividad_ejemplo),
                        color = TextPrimary,
                        fontSize = 17.sp
                    )

                    Text(
                        text = stringResource(R.string.hora_ejemplo),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    navController.navigate(AppScreens.Actividades.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.ver_actividades))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(AppScreens.Horarios.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.ver_horarios))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(AppScreens.Recordatorios.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.ver_recordatorios))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(AppScreens.Ajustes.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.ver_ajustes))
            }
        }
    }
}
