package com.eachubkov.newsapp2.data.network.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleResponse>
)