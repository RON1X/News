package com.eachubkov.newsapp2.utils

class Constants {
    companion object {
        const val API_KEY = "d6e12a7383874bb1b34732df6d7eb2b8"
        const val BASE_URL = "https://newsapi.org/v2/"

        const val BIG_CARD_NEWS = 0
        const val SMALL_CARD_NEWS = 1

        const val INTERNET_LOST = "Отсутствует подключение к сети"
        const val INTERNET_RESTORED = "Подключение к интернету восттановлено"

        const val ERROR_400 = "400 - The request was unacceptable, often due to a missing or misconfigured"
        const val ERROR_401 = "401 - Your API key was missing from the request, or wasn't correct."
        const val ERROR_429 = "429 - You made too many requests within a window of time and have been rate limited. Back off for a while."
        const val ERROR_500 = "500 - Something went wrong on our side."
    }
}