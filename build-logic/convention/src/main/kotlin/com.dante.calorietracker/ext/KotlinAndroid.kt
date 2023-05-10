package com.dante.calorietracker.ext

import com.android.build.api.dsl.CommonExtension
import com.dante.calorietracker.Config.COMPILE_SDK
import com.dante.calorietracker.Config.MIN_SDK
import com.dante.calorietracker.LibsVal.ANDROID_DESUGAR_JDK_LIBS
import com.dante.calorietracker.LibsVal.LIBS
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

const val CORE_LIBRARY_DESUGARING = "coreLibraryDesugaring"

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        compileSdk = COMPILE_SDK

        defaultConfig {
            minSdk = MIN_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
        packaging {
            // Exclude some licenses from the APK
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/LICENSE-notice.md")
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            // Set JVM target to 17
            jvmTarget = JavaVersion.VERSION_17.toString()
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
    dependencies {
        add(CORE_LIBRARY_DESUGARING, libs.findLibrary(ANDROID_DESUGAR_JDK_LIBS).get())
    }
}
