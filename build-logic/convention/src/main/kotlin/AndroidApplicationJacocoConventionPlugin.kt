import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.dante.calorietracker.PluginId.ANDROID_APPLICATION_PLUGIN_ID
import com.dante.calorietracker.PluginId.JACOCO_PLUGIN_ID
import com.dante.calorietracker.ext.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(JACOCO_PLUGIN_ID)
                apply(ANDROID_APPLICATION_PLUGIN_ID)
            }
            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}
