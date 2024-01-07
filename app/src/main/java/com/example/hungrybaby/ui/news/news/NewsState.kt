package com.example.hungrybaby.ui.news.news

import com.example.hungrybaby.model.News

sealed interface NewsApiState {
    data class Success(val news: List<News>) : NewsApiState

    object Error : NewsApiState

    object Loading : NewsApiState
}
