package mx.uttt.uniplanfocus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val UniPlanColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleDark,
    background = LavenderBackground,
    surface = White,
    onPrimary = White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun UniPlanFocusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = UniPlanColorScheme,
        typography = Typography,
        content = content
    )
}
