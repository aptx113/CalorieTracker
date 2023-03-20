package com.dante.calorietracker

object Config {
    const val APP_ID = "com.dante.calorietracker"
    const val COMPILE_SDK = 33
    const val MIN_SDK = 24
    const val TARGET_SDK = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val TEST_INSTRUMENTATION_RUNNER =
        "com.dante.calorietracker.core.testing.CalorieTrackerTestRunner"

    const val CORE_COMMON_NAME_SPACE = "com.dante.calorietracker.core.common"
    const val CORE_DATA_NAME_SPACE = "com.dante.calorietracker.core.data"
    const val CORE_DOMAIN_NAME_SPACE = "com.dante.calorietracker.core.domain"
    const val CORE_TESTING_NAME_SPACE = "com.dante.calorietracker.core.testing"
    const val CORE_UI_NAME_SPACE = "com.dante.calorietracker.core.ui"

    const val FEATURE_ONBORADING_NAME_SPACE = "com.dante.calorietracker.feature.onboarding"
    const val FEATURE_TRACKER_NAME_SPACE = "com.dante.calorietracker.feature.tracker"

    const val UI_TEST_HILT_NAME_SPACE = "com.dante.calorietracker.ui_test_hilt_manifest"
}
