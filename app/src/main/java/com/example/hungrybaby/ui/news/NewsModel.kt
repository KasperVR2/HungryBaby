package com.example.hungrybaby.ui.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hungrybaby.network.NewsApi.newsService
import com.example.hungrybaby.network.asDomainObjects
import com.example.hungrybaby.ui.news.news.NewsApiState
import kotlinx.coroutines.launch

class NewsModel() : ViewModel() {
    var newsApiState: NewsApiState by mutableStateOf(NewsApiState.Loading)
        private set

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val result = newsService.getNews()
                newsApiState = NewsApiState.Success(result.asDomainObjects())
            } catch (e: Exception) {
                newsApiState = NewsApiState.Error
            }
        }
    }
}
