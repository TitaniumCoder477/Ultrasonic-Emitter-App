import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ultrasonic_emitter_app.composeapp.generated.resources.Res
import ultrasonic_emitter_app.composeapp.generated.resources.icon2

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    val state = rememberWindowState(
        size = DpSize(412.dp, 892.dp),
        position = WindowPosition(300.dp, 300.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ultrasonic Emitter App",
        state = state,
        icon = painterResource(Res.drawable.icon2)
    ) {
        App()
    }
}