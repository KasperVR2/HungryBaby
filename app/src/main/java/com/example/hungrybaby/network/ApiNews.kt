package com.example.hungrybaby.network

import com.example.hungrybaby.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiNews(val news: String)

fun Flow<List<ApiNews>>.asDomainObjects(): Flow<List<News>> {
    var domainList =
        this.map {
            it.asDomainObjects()
        }
    return domainList
}

fun List<ApiNews>.asDomainObjects(): List<News>  {
    var domainList =
        this.map {
            News(it.news)
        }
    return domainList
}
