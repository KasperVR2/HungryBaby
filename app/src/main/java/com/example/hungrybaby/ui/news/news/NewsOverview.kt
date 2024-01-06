package com.example.hungrybaby.ui.news.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsOverview() {
    val news = "In the making" // NewsService()
    Box(
        modifier =
            Modifier.border(1.dp, color = androidx.compose.ui.graphics.Color.Black)
                .background(androidx.compose.ui.graphics.Color.White)
                .shadow(1.dp),
    ) {
        Text(
            "News: $news",
            modifier = Modifier.padding(12.dp),
            style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
        )
    }
}
