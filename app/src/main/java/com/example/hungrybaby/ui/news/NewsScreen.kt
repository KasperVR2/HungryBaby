package com.example.hungrybaby.ui.news

import androidx.compose.runtime.Composable
import com.example.hungrybaby.ui.news.news.NewsOverview

@Composable
fun News(newsModel: NewsModel = NewsModel()) {
    NewsOverview(newsModel)
}
