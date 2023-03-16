import com.android.build.api.dsl.ApplicationExtension
import com.dante.calorietracker.Config.TARGET_SDK
import com.dante.calorietracker.PluginId.ANDROID_APPLICATION_PLUGIN_ID
import com.dante.calorietracker.PluginId.KOTLIN_ANDROID_PLUGIN_ID
import com.dante.calorietracker.ext.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin:Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply(ANDROID_APPLICATION_PLUGIN_ID)
                apply(KOTLIN_ANDROID_PLUGIN_ID)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK
            }
        }
    }
}
