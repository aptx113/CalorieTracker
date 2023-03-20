import com.dante.calorietracker.Config

plugins {
    id("app.android.feature")
    id("app.android.library.compose")
    id("app.android.library.jacoco")
}

android {
    namespace = Config.FEATURE_TRACKER_NAME_SPACE
}
