package com.dante.calorietracker.ext

import com.dante.calorietracker.LibsVal.ANDROID_TEST_IMPLEMENTATION
import com.dante.calorietracker.LibsVal.IMPLEMENTATION
import com.dante.calorietracker.LibsVal.KSP
import com.dante.calorietracker.LibsVal.TEST_IMPLEMENTATION
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementAndroidTest(dependencyNotation: Any) = add(
    ANDROID_TEST_IMPLEMENTATION, dependencyNotation
)

fun DependencyHandlerScope.implement(dependencyNotation: Any) =
    add(IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.implementTest(dependencyNotation: Any) =
    add(TEST_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.ksp(dependencyNotation: Any) = add(KSP, dependencyNotation)
