package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color
import com.example.recyclerviewcompose.R

object DataSource {
    val gridSizeOptions = listOf<Pair<String, Int>>(
        Pair("Standard (7X6)", 0),
        Pair("Small (6X5)", 1),
        Pair("Large (8X7)", 2)
    )

    val colorOptions = listOf<Color>(
        Color(0xFFFFA500),
        Color(0xFFFFFFFF),
        Color(0xFF421568),
        Color(0xFFF500FF),
        Color(0xFFBA1A1A),
        Color(0xFFFFDAD6),
        Color(0xFF00FFFF),
        Color(0xFFF9DB22),
        Color(0xFF000000),
        Color(0xFF298b28),

    )

    val playerOrAIOptions = listOf<Pair<String, Boolean>>(
        Pair("VSPLAYER", true),
        Pair("VSAI", false),/* referenced by output of playervsai screen output to set boolean for ai or player flag*/
    )

    var avatarImages = listOf(
        R.drawable.vik,
        R.drawable.ekko,
        R.drawable.jayce,
        R.drawable.jinx,
        R.drawable.silco,
        R.drawable.lucy,
    )
}