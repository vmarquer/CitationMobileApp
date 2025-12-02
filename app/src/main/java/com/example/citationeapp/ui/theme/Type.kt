package com.example.citationeapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.citationeapp.R

//-----------  Familles de police -----------//
val barlowFontFamily = FontFamily(
    Font(R.font.barlowregular)
)

//-----------  Styles de texte -----------//
val h1 = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 32.sp,
)

val h2 = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 24.sp,
)

val h3 = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 18.sp,
)

val h1Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold
)

val h2Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold
)

val h3Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold
)

val body1Regular = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 16.sp
)

val body1Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold
)

val body1Italic = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 16.sp,
    fontStyle = FontStyle.Italic
)

val body2Regular = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 14.sp
)

val body2Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold
)

val body2Italic = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 14.sp,
    fontStyle = FontStyle.Italic
)

val body3Regular = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 12.sp
)

val body3Bold = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold
)

val body3Italic = TextStyle(
    fontFamily = barlowFontFamily,
    fontSize = 12.sp,
    fontStyle = FontStyle.Italic
)
