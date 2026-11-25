
package mx.uttt.uniplanfocus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.uttt.uniplanfocus.ui.theme.*

@Composable
fun AjustesScreen(navController: NavHostController) {

    Scaffold(
        containerColor = LavenderBackground
    ) { espacio ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(24.dp)
        ) {

            Text(
                text = stringResource(R.string.titulo_ajustes),
                color = PurplePrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.descripcion_ajustes),
                color = TextSecondary,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

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
                        text = stringResource(R.string.nombre_aplicacion),
                        color = TextPrimary,
                        fontSize = 18.sp
                    )

                    Text(
                        text = stringResource(R.string.version_aplicacion),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.regresar))
            }
        }
    }
}
