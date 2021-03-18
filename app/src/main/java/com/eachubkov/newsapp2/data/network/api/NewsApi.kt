package com.eachubkov.newsapp2.data.network.api

import com.eachubkov.newsapp2.data.network.models.NewsResponse
import com.eachubkov.newsapp2.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String = "ru",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getCategory(
        @Query("category")
        category: String,
        @Query("country")
        countryCode: String = "ru",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q")
        query: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "ru"
    ): Response<NewsResponse>
}