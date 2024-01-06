package com.example.hungrybaby.ui.news.news

import com.example.hungrybaby.model.News

data class NewsState(
    val newsMessage: String = "",
)

data class NewsListState(val foodList: List<News> = listOf())
