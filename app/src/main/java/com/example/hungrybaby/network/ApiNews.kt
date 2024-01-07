package com.example.hungrybaby.network

import com.example.hungrybaby.model.News
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiNews(val news: String)

fun List<ApiNews>.asDomainObjects() = map { News(it.news) }
