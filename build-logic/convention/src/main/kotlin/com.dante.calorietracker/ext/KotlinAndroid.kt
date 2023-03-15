package com.dante.calorietracker.ext

import com.android.build.api.dsl.CommonExtension
import com.dante.calorietracker.Config.COMPILE_SDK
import com.dante.calorietracker.Config.MIN_SDK
import com.dante.calorietracker.LibsVal.ANDROID_DESUGAR_JDK_LIBS
import com.dante.calorietracker.LibsVal.LIBS
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

const val KOTLIN_OPTIONS = "kotlinOptions"
const val CORE_LIBRARY_DESUGARING = "coreLibraryDesugaring"

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        compileSdk = COMPILE_SDK

        defaultConfig {
            minSdk = MIN_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
    dependencies {
        add(CORE_LIBRARY_DESUGARING, libs.findLibrary(ANDROID_DESUGAR_JDK_LIBS).get())
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure(KOTLIN_OPTIONS, block)
}
