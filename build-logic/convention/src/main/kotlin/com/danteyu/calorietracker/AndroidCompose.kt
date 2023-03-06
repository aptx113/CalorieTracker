package com.danteyu.calorietracker

import com.android.build.api.dsl.CommonExtension
import com.danteyu.calorietracker.constants.LibsConst.ANDROIDX_COMPOSE_BOM
import com.danteyu.calorietracker.constants.LibsConst.ANDROID_TEST_IMPLEMENTATION
import com.danteyu.calorietracker.constants.LibsConst.IMPLEMENTATION
import com.danteyu.calorietracker.constants.LibsConst.LIBS
import com.danteyu.calorietracker.constants.VersionsConst.ANDROIDX_COMPOSE_COMPILER
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion(ANDROIDX_COMPOSE_COMPILER).get().toString()
        }

        dependencies {
            val bom = libs.findLibrary(ANDROIDX_COMPOSE_BOM).get()
            add(IMPLEMENTATION, platform(bom))
            add(ANDROID_TEST_IMPLEMENTATION, platform(bom))
        }
    }
}
