package com.eachubkov.newsapp2.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eachubkov.newsapp2.data.Repository
import com.eachubkov.newsapp2.data.network.models.NewsResponse
import com.eachubkov.newsapp2.utils.Constants.Companion.ERROR_400
import com.eachubkov.newsapp2.utils.Constants.Companion.ERROR_401
import com.eachubkov.newsapp2.utils.Constants.Companion.ERROR_429
import com.eachubkov.newsapp2.utils.Constants.Companion.ERROR_500
import com.eachubkov.newsapp2.utils.Constants.Companion.INTERNET_LOST
import com.eachubkov.newsapp2.utils.Constants.Companion.INTERNET_RESTORED
import com.eachubkov.newsapp2.utils.NetworkResult
import com.eachubkov.newsapp2.utils.hasInternetConnection
import com.eachubkov.newsapp2.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository, application: Application): AndroidViewModel(application) {

    private val _newsResponse: MutableLiveData<NetworkResult<NewsResponse>> = MutableLiveData()
    val newsResponse: LiveData<NetworkResult<NewsResponse>> get() = _newsResponse

    var backOnline = false

    fun getNews() = viewModelScope.launch {
        getNewsSafeCall()
    }

    fun getCategory(category: String) = viewModelScope.launch {
        getCategorySafeCall(category)
    }

    fun searchNews(query: String) = viewModelScope.launch {
        searchNewsSafeCall(query)
    }

    private suspend fun getNewsSafeCall() {
        _newsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication<Application>())) {
            _newsResponse.value = handleNewsResponse(response = repository.getNews())
        } else {
            _newsResponse.value = NetworkResult.Error(INTERNET_LOST)
        }
    }

    private suspend fun getCategorySafeCall(category: String) {
        _newsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication<Application>())) {
            _newsResponse.value = handleNewsResponse(response = repository.getCategory(category))
        } else {
            _newsResponse.value = NetworkResult.Error(INTERNET_LOST)
        }
    }

    private suspend fun searchNewsSafeCall(query: String) {
        _newsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication<Application>())) {
            _newsResponse.value = handleNewsResponse(response = repository.searchNews(query))
        } else {
            _newsResponse.value = NetworkResult.Error(INTERNET_LOST)
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): NetworkResult<NewsResponse> {
        return when {
            response.isSuccessful -> NetworkResult.Success(response.body()!!)
            response.code() == 400 -> NetworkResult.Error(ERROR_400)
            response.code() == 401 -> NetworkResult.Error(ERROR_401)
            response.code() == 429 -> NetworkResult.Error(ERROR_429)
            response.code() == 500 -> NetworkResult.Error(ERROR_500)
            else -> NetworkResult.Error(response.message())
        }
    }

    fun showNetworkStatus(status: Boolean) {
        if (status && backOnline) {
            getApplication<Application>().toast(INTERNET_RESTORED)
            backOnline = false
        } else if (!status) {
            getApplication<Application>().toast(INTERNET_LOST)
            backOnline = true
        }
    }
}