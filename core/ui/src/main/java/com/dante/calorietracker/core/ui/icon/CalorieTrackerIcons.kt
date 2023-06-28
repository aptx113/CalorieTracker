package com.dante.calorietracker.core.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

object CalorieTrackerIcons {
    val Add = Icons.Default.Add
    val ArrowBack = Icons.Default.ArrowBack
    val ArrowDown = Icons.Default.KeyboardArrowDown
    val ArrowForward = Icons.Default.ArrowForward
    val ArrowUp = Icons.Default.KeyboardArrowUp
    val Check = Icons.Default.Check
    val Close = Icons.Default.Close
    val Search = Icons.Default.Search
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
