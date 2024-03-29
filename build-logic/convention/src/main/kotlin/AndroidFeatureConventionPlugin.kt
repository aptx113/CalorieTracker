import com.android.build.api.dsl.LibraryExtension
import com.dante.calorietracker.LibsVal.COIL_KT
import com.dante.calorietracker.LibsVal.COIL_KT_COMPOSE
import com.dante.calorietracker.LibsVal.COROUTINES
import com.dante.calorietracker.LibsVal.HILT_NAV_COMPOSE
import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.LibsVal.LIFECYCLE_RUNTIME_COMPOSE
import com.dante.calorietracker.LibsVal.LIFECYCLE_VIEWMODEL_COMPOSE
import com.dante.calorietracker.LibsVal.TEST
import com.dante.calorietracker.ModulePath.CORE_COMMON
import com.dante.calorietracker.ModulePath.CORE_DATA
import com.dante.calorietracker.ModulePath.CORE_DOMAIN
import com.dante.calorietracker.ModulePath.CORE_MODEL
import com.dante.calorietracker.ModulePath.CORE_TESTING
import com.dante.calorietracker.ModulePath.CORE_UI
import com.dante.calorietracker.PluginId.ANDROID_HILT_REGISTER_ID
import com.dante.calorietracker.PluginId.ANDROID_LIBRARY_REGISTER_ID
import com.dante.calorietracker.ext.implement
import com.dante.calorietracker.ext.implementAndroidTest
import com.dante.calorietracker.ext.implementTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(ANDROID_LIBRARY_REGISTER_ID)
                apply(ANDROID_HILT_REGISTER_ID)
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.dante.calorietracker.core.testing.CalorieTrackerTestRunner"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)

            dependencies {
                implement(project(CORE_COMMON))
                implement(project(CORE_DATA))
                implement(project(CORE_DOMAIN))
                implement(project(CORE_MODEL))
                implement(project(CORE_UI))

                implementTest(kotlin(TEST))
                implementTest(project(CORE_TESTING))
                implementAndroidTest(kotlin(TEST))
                implementAndroidTest(project(CORE_TESTING))
                implementAndroidTest(project(CORE_UI))

                implement(libs.findLibrary(COIL_KT).get())
                implement(libs.findLibrary(COIL_KT_COMPOSE).get())

                implement(libs.findLibrary(HILT_NAV_COMPOSE).get())
                implement(libs.findLibrary(LIFECYCLE_RUNTIME_COMPOSE).get())
                implement(libs.findLibrary(LIFECYCLE_VIEWMODEL_COMPOSE).get())

                implement(libs.findLibrary(COROUTINES).get())
            }
        }
    }
}
