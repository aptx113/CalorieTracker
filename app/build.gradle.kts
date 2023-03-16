import com.dante.calorietracker.BuildType
import com.dante.calorietracker.Config
import com.dante.calorietracker.LibsVal.TEST

plugins {
    id("app.android.application")
    id("app.android.application.compose")
    id("app.android.application.flavors")
    id("app.android.application.jacoco")
    id("app.android.hilt")
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = Config.APP_ID
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = BuildType.debug.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.release.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = Config.APP_ID
}

dependencies {
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(kotlin(TEST))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.window.manager)

    implementation(libs.coil.kt)
}


configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
    }
}
