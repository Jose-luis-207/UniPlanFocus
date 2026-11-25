
package mx.uttt.uniplanfocus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.uttt.uniplanfocus.ui.theme.UniPlanFocusTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UniPlanFocusTheme {
                Navegacion()
            }
        }
    }
}

@Composable
fun Navegacion() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Inicio.route
    ) {

        composable(AppScreens.Inicio.route) {
            InicioScreen(navController)
        }

        composable(AppScreens.Actividades.route) {
            ActividadesScreen(navController)
        }

        composable(AppScreens.Horarios.route) {
            HorariosScreen(navController)
        }

        composable(AppScreens.Recordatorios.route) {
            RecordatoriosScreen(navController)
        }

        composable(AppScreens.Ajustes.route) {
            AjustesScreen(navController)
        }
    }
}
