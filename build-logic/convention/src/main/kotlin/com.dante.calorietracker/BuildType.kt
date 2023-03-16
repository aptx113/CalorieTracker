package com.dante.calorietracker

enum class BuildType(val applicationIdSuffix: String? = null) {
    debug(".debug"),
    release,
}
