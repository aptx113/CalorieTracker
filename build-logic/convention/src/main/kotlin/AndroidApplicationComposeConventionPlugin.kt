import com.android.build.api.dsl.ApplicationExtension
import com.dante.calorietracker.PluginId.ANDROID_APPLICATION_PLUGIN_ID
import com.dante.calorietracker.ext.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(ANDROID_APPLICATION_PLUGIN_ID)
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}
