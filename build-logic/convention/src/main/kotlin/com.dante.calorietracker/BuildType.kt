package com.dante.calorietracker

@Suppress("unused", "EnumEntryName")
enum class BuildType(val applicationIdSuffix: String? = null) {
    debug(".debug"),
    release,
}
