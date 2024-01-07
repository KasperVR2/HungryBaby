package com.example.hungrybaby.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface NewsApiService {
    @GET("news")
    suspend fun getNews(): List<ApiNews>
}

private val baseUrl = "http://10.0.2.2:3000"
private var retrofit =
    Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .build()

object NewsApi {
    val newsService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}
