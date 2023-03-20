import com.dante.calorietracker.Config

plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = Config.UI_TEST_HILT_NAME_SPACE
}
