import com.dante.calorietracker.Config
import com.dante.calorietracker.ModulePath.CORE_COMMON
import com.dante.calorietracker.ModulePath.CORE_MODEL
import com.dante.calorietracker.ModulePath.CORE_TESTING

plugins {
    id("app.android.library")
    id("app.android.library.jacoco")
    id("kotlinx-serialization")
    id("app.android.hilt")
}

android {
    namespace = Config.CORE_DATA_NAME_SPACE
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(CORE_COMMON))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(CORE_MODEL))
    implementation(project(":core:network"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(project(CORE_TESTING))
}
