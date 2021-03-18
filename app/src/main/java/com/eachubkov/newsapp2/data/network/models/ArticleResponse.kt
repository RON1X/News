package com.eachubkov.newsapp2.data.network.models

data class ArticleResponse(
    val source: SourceResponse?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)