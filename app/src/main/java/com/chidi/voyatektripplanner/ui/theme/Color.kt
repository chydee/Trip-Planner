package com.chidi.voyatektripplanner.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val White = Color(0xFFFFFFFF)
val White60 = Color(0xFFF0F2F5)
val White80 = Color(0xFFF7F9FC)
val Blue60 = Color(0xFF0D6EFD)
val BlueLight10 = Color(0xFFEDF7F9)
val BlueLight40 = Color(0xFFE7F0FF)
val BlueLight80 = Color(0xFFCFE2FF)
val BlueDark10 = Color(0xFF676E7E)
val BlueDark40 = Color(0xFF344054)
val BlueDark80 = Color(0xFF1D2433)
val Black = Color(0xFF000000)

val DarkCustomColors = CustomColors(
)
val LocalCustomColors = staticCompositionLocalOf {
    CustomColors()
}

@Immutable
data class CustomColors(
    val white60: Color = White60,
    val white80: Color = White80,
    val blue60: Color = Blue60,
    val blueLight10: Color = BlueLight10,
    val blueLight40: Color = BlueLight40,
    val blueLight80: Color = BlueLight80,
    val blueDark10: Color = BlueDark10,
    val blueDark40: Color = BlueDark40,
    val blueDark80: Color = BlueDark80,
    val black: Color = Black,
    val white: Color = White,
)