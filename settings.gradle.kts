pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CalorieTracker"
include( ":app")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:testing")
include(":core:ui")
