import com.android.build.gradle.TestExtension
import com.dante.calorietracker.Config.TARGET_SDK
import com.dante.calorietracker.PluginId.KOTLIN_ANDROID_PLUGIN_ID
import com.dante.calorietracker.PluginId.TEST_PLUGIN_ID
import com.dante.calorietracker.ext.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(TEST_PLUGIN_ID)
                apply(KOTLIN_ANDROID_PLUGIN_ID)
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK
            }
        }
    }
}
