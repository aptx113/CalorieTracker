import com.android.build.gradle.LibraryExtension
import com.dante.calorietracker.PluginId.ANDROID_LIBRARY_PLUGIN_ID
import com.dante.calorietracker.ext.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(ANDROID_LIBRARY_PLUGIN_ID)
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
