package com.eachubkov.newsapp2.data

import com.eachubkov.newsapp2.data.network.api.NewsApi
import com.eachubkov.newsapp2.data.network.models.NewsResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(): Response<NewsResponse> = newsApi.getNews()
    suspend fun getCategory(category: String): Response<NewsResponse> = newsApi.getCategory(category)
    suspend fun searchNews(query: String): Response<NewsResponse> = newsApi.searchNews(query)
}