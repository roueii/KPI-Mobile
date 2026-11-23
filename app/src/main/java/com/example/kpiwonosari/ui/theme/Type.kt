package com.example.kpiwonosari.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.kpiwonosari.R

val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_extrabold, FontWeight.ExtraBold),
)

/** Shorthand used across screens: Plus Jakarta Sans at a given size/weight/line height. */
fun jakarta(
    size: Int,
    weight: FontWeight = FontWeight.Normal,
    lineHeight: Int? = null,
    letterSpacing: Double = 0.0,
): TextStyle = TextStyle(
    fontFamily = PlusJakartaSans,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight?.sp ?: TextUnit.Unspecified,
    letterSpacing = letterSpacing.sp,
)

val Typography = Typography(
    bodyLarge = jakarta(16, lineHeight = 24),
    bodyMedium = jakarta(14, lineHeight = 20),
    bodySmall = jakarta(12, lineHeight = 16),
    titleLarge = jakarta(20, FontWeight.Bold, 28),
    titleMedium = jakarta(16, FontWeight.Bold, 22),
    labelLarge = jakarta(14, FontWeight.SemiBold, 20),
    labelSmall = jakarta(11, FontWeight.SemiBold, 14),
)
