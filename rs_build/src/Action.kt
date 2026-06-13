import org.jetbrains.amper.plugins.Input
import org.jetbrains.amper.plugins.Output
import org.jetbrains.amper.plugins.TaskAction
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi


@TaskAction
@OptIn(ExperimentalPathApi::class)
fun generateLibSources(
    @Input libsDir: Path,
    @Output resourcesDir: Path
) {
    println("Generating libraries")
    ProcessBuilder(
        "cmd", "/c", "cd $libsDir && copy target\\release\\*rslib* resources"
    ).start().waitFor()
    println("Generated libraries")
}
