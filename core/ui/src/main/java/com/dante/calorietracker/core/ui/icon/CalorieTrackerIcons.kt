package com.dante.calorietracker.core.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

object CalorieTrackerIcons {

}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
