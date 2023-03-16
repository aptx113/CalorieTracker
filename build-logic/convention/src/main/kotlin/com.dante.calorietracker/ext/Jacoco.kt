package com.dante.calorietracker.ext

import com.android.build.api.variant.AndroidComponentsExtension
import com.dante.calorietracker.LibsVal.LIBS
import com.dante.calorietracker.VersionVal.JACOCO_VER
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

private const val JACOCO_TEST_REPORT = "jacocoTestReport"

private val coverageExclusions = listOf(
    // Android
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*"
)

internal fun Project.configureJacoco(androidComponentsExtension: AndroidComponentsExtension<*, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)

    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion(JACOCO_VER).get().toString()
    }

    val jacocoTestReport = tasks.create(JACOCO_TEST_REPORT)

    androidComponentsExtension.onVariants { variant ->
        val testTaskName = "test${variant.name.capitalized()}UnitTest"

        val reportTask =
            tasks.register("jacoco${testTaskName.capitalized()}Report", JacocoReport::class) {
                dependsOn(testTaskName)

                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                classDirectories.setFrom(
                    fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                        exclude(coverageExclusions)
                    }
                )

                sourceDirectories.setFrom(
                    files(
                        "$projectDir/src/main/java",
                        "$projectDir/src/main/kotlin"
                    )
                )
                executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
            }

        jacocoTestReport.dependsOn(reportTask)
    }
}
