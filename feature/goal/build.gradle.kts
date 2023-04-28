import com.dante.calorietracker.Config

plugins {
    id("app.android.library.compose")
    id("app.android.library.jacoco")
    id("app.android.feature")
}

android {
    namespace = Config.goalNameSpace
}
