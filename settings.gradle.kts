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
include(":core:testing")
include(":core:ui")
include(":feature:age")
include(":feature:activity")
include(":feature:gender")
include(":feature:goal")
include(":feature:height")
include(":feature:nutrientgoal")
include(":feature:search")
include(":feature:tracker")
include(":feature:weight")
include(":feature:welcome")
include(":ui-test-hilt-manifest")
include(":core:datastore")
include(":core:model")
