import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.LibsVal.ROOM_COMPILER
import com.dante.calorietracker.LibsVal.ROOM_KTX
import com.dante.calorietracker.LibsVal.ROOM_RUNTIME
import com.dante.calorietracker.PluginId.KSP_PLUGIN_ID
import com.dante.calorietracker.ext.implement
import com.dante.calorietracker.ext.ksp
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(KSP_PLUGIN_ID)

            extensions.configure<KspExtension> {
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            dependencies {
                implement(libs.findLibrary(ROOM_RUNTIME).get())
                implement(libs.findLibrary(ROOM_KTX).get())
                ksp(libs.findLibrary(ROOM_COMPILER).get())

            }
        }
    }

    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}
