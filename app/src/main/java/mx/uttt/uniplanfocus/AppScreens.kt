
package mx.uttt.uniplanfocus

sealed class AppScreens(val route: String) {

    object Inicio : AppScreens("inicio")

    object Actividades : AppScreens("actividades")

    object Horarios : AppScreens("horarios")

    object Recordatorios : AppScreens("recordatorios")

    object Ajustes : AppScreens("ajustes")
}
