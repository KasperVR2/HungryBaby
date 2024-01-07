package com.example.hungrybaby.ui.news

import androidx.compose.runtime.Composable
import com.example.hungrybaby.ui.news.news.NewsOverview

@Composable
fun News(newsModel: NewsModel = NewsModel()) {
    // Little lonely here... but maybe we'll add more to this screen later.
    NewsOverview(newsModel)
}
