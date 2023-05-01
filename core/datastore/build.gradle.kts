import com.dante.calorietracker.Config

plugins {
    id("app.android.library")
    id("app.android.library.jacoco")
    id("app.android.hilt")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = Config.coreDataStoreNameSpace
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core:testing"))
}
