package com.example.hungrybaby.ui.news.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.news.NewsModel

@Composable
fun NewsOverview(newsModel: NewsModel) {
    val taskApiState = newsModel.newsApiState
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(text = stringResource(R.string.news_caption), style = MaterialTheme.typography.titleMedium)
        }
        when (taskApiState) {
            is NewsApiState.Success -> {
                items(taskApiState.news) { news ->
                    NewsItem(news.news)
                }
            }
            is NewsApiState.Error -> {
                item {
                    Text("Error loading the news items... try again later!")
                }
            }

            is NewsApiState.Loading -> {
                item {
                    Text("Loading...")
                }
            }
        }
    }
}
