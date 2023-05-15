import com.dante.calorietracker.Config
import com.dante.calorietracker.ModulePath

plugins {
    id("app.android.library")
    id("app.android.library.compose")
    id("app.android.hilt")
}

android {
    namespace = Config.CORE_TESTING_NAME_SPACE
}

dependencies {
    implementation(project(ModulePath.CORE_COMMON))
    implementation(project(ModulePath.CORE_DATA))
    implementation(project(ModulePath.CORE_DOMAIN))
    implementation(project(ModulePath.CORE_MODEL))
    implementation(project(":core:datastore"))
    api(libs.androidx.dataStore.preferences)

    implementation(libs.kotlinx.datetime)

    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.mockk)
    api(libs.mockk.android)

    debugApi(libs.androidx.compose.ui.testManifest)
}
