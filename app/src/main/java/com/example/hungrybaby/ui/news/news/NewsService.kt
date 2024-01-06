package com.example.hungrybaby.ui.news.news

import com.example.hungrybaby.network.ApiNews
import com.example.hungrybaby.network.NewsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.security.AccessController.getContext

suspend fun NewsService(): List<ApiNews> {
    val context = getContext()
    val baseUrl = "http://10.0.2.2:3000"
    val retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType()),
            )
            .baseUrl(baseUrl)
            .build()

    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    return retrofitService.getNews()
}
