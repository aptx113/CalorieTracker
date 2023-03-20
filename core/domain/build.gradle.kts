import com.dante.calorietracker.Config
import com.dante.calorietracker.ModulePath.CORE_DATA
import com.dante.calorietracker.ModulePath.CORE_MODEL
import com.dante.calorietracker.ModulePath.CORE_TESTING

plugins {
    id("app.android.library")
    id("app.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = Config.CORE_DOMAIN_NAME_SPACE
}

dependencies {

    implementation(project(CORE_DATA))
    implementation(project(CORE_MODEL))

    testImplementation(project(CORE_TESTING))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
