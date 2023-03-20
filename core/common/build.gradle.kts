import com.dante.calorietracker.Config
import com.dante.calorietracker.ModulePath

plugins {
    id("app.android.library")
    id("app.android.library.jacoco")
    id("app.android.hilt")
}

android {
    namespace = Config.CORE_COMMON_NAME_SPACE
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(ModulePath.CORE_TESTING))
}
