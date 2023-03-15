import com.android.build.gradle.LibraryExtension
import com.dante.calorietracker.Config.TARGET_SDK
import com.dante.calorietracker.LibsVal.JUNIT4
import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.LibsVal.TEST
import com.dante.calorietracker.PluginId.ANDROID_LIBRARY_PLUGIN_ID
import com.dante.calorietracker.PluginId.KOTLIN_ANDROID_PLUGIN_ID
import com.dante.calorietracker.configureFlavors
import com.dante.calorietracker.ext.configureKotlinAndroid
import com.dante.calorietracker.ext.implementAndroidTest
import com.dante.calorietracker.ext.implementTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(ANDROID_LIBRARY_PLUGIN_ID)
                apply(KOTLIN_ANDROID_PLUGIN_ID)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK
                configureFlavors(this)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary(JUNIT4).get())
                }
            }
            dependencies {
                implementAndroidTest(kotlin(TEST))
                implementTest(kotlin(TEST))
            }
        }
    }
}
