package com.eachubkov.newsapp2.ui.models

import android.annotation.SuppressLint
import android.os.Parcelable
import com.eachubkov.newsapp2.data.network.models.ArticleResponse
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class ArticleUI(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
): Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun ArticleResponse.mapToUI(): ArticleUI {
    return ArticleUI(
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt.let {
            SimpleDateFormat("dd.MM.yyyy hh:mm").format(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(it))
        }
    )
}

