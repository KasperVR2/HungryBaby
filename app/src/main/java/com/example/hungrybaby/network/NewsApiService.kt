package com.example.hungrybaby.network

import retrofit2.http.GET

interface NewsApiService {
    @GET("news")
    suspend fun getNews(): List<ApiNews>
}
