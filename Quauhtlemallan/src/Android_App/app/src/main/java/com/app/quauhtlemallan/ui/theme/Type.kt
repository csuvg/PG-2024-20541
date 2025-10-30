package com.app.quauhtlemallan.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.quauhtlemallan.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val cinzelFontFamily = FontFamily(
    Font(R.font.cinzel_regular, FontWeight.Normal),
    Font(R.font.cinzel_medium, FontWeight.Medium),
    Font(R.font.cinzel_semibold, FontWeight.SemiBold),
    Font(R.font.cinzel_bold, FontWeight.Bold),
    Font(R.font.cinzel_extrabold, FontWeight.ExtraBold),
    Font(R.font.cinzel_black, FontWeight.Black)
)

val rubikFontFamily = FontFamily(
    Font(R.font.rubik_black, FontWeight.Black),
    Font(R.font.rubik_blackitalic, FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_bolditalic, FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.rubik_extrabold, FontWeight.ExtraBold),
    Font(R.font.rubik_extrabolditalic, FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.rubik_italic, style = FontStyle.Italic),
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_lightitalic, FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.rubik_medium, FontWeight.Medium),
    Font(R.font.rubik_mediumitalic, FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_semibold, FontWeight.SemiBold),
    Font(R.font.rubik_semibolditalic, FontWeight.SemiBold, style = FontStyle.Italic),
)