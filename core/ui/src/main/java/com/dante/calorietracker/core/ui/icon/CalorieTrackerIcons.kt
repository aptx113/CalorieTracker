package com.dante.calorietracker.core.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.graphics.vector.ImageVector

object CalorieTrackerIcons {
    val ArrowBack = Icons.Default.ArrowBack
    val ArrowForward = Icons.Default.ArrowForward
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
