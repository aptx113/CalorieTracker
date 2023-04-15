package com.dante.calorietracker.ext

import com.android.build.api.dsl.CommonExtension
import com.dante.calorietracker.LibsVal.COMPOSE_BOM
import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.VersionVal.COMPOSE_COMPILER_VER
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

const val COMPOSE_METRICS = "compose-metrics"
const val COMPOSE_REPORTS = "compose-reports"
const val ENABLE_COMPOSE_COMPILER_METRICS = "enableComposeCompilerMetrics"
const val ENABLE_COMPOSE_COMPILER_REPORTS = "enableComposeCompilerReports"

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion(COMPOSE_COMPILER_VER).get().toString()
        }

        dependencies {
            val bom = libs.findLibrary(COMPOSE_BOM).get()
            implement(platform(bom))
            implementAndroidTest(platform(bom))
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty(ENABLE_COMPOSE_COMPILER_METRICS)
    val enableMetrics = (enableMetricsProvider.orNull == "true")
    if (enableMetrics) {
        val metricsFolder = File(project.buildDir, COMPOSE_METRICS)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
        )
    }

    val enableReportsProvider = project.providers.gradleProperty(ENABLE_COMPOSE_COMPILER_REPORTS)
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = File(project.buildDir, COMPOSE_REPORTS)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
        )
    }
    return metricParameters.toList()
}
