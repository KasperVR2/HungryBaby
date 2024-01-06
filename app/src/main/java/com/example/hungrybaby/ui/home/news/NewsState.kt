package com.example.hungrybaby.ui.home.news

import com.example.hungrybaby.model.News

data class NewsState(
    val newsMessage: String = "",
)

data class NewsListState(val foodList: List<News> = listOf())
