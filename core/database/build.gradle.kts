plugins {
    id("app.android.library")
    id("app.android.library.jacoco")
    id("app.android.hilt")
    id("app.android.room")
}

android {
    namespace = "com.dante.calorietracker.core.database"

    defaultConfig {
        testInstrumentationRunner = "com.dante.calorietracker.core.testing.CalorieTrackerTestRunner"
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}
