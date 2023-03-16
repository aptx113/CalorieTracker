import com.dante.calorietracker.LibsVal.HILT_ANDROID
import com.dante.calorietracker.LibsVal.HILT_COMPILER
import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.PluginId.DAGGER_HILT_ANDROID_PLUGIN_ID
import com.dante.calorietracker.PluginId.KOTLIN_KAPT_PLUGIN_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(DAGGER_HILT_ANDROID_PLUGIN_ID)
                apply(KOTLIN_KAPT_PLUGIN_ID)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            dependencies {
                "implementation"(libs.findLibrary(HILT_ANDROID).get())
                "kapt"(libs.findLibrary(HILT_COMPILER).get())
                "kaptAndroidTest"(libs.findLibrary(HILT_COMPILER).get())
            }
        }
    }
}
