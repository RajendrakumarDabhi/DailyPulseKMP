package com.rajendra.dailypulsekmp.presentation.viewmodel

import com.rajendra.dailypulsekmp.data.network.NewsApiService
import com.rajendra.dailypulsekmp.data.network.createHttpClient
import com.rajendra.dailypulsekmp.domain.model.NewsApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel : BaseViewModel() {
    private val _state: MutableStateFlow<NewsScreenState> =
        MutableStateFlow(NewsScreenState.Loading)
    val screenState: StateFlow<NewsScreenState> = _state
    private val newsApiService = NewsApiService(createHttpClient())


    suspend fun getNewsList() {
        _state.emit(NewsScreenState.Loading)
        val result = newsApiService.getEverythingWithQuery(
            query = "news")
        if (result.isSuccess) {
            val resultData = result.getOrNull()?.articles ?: emptyList<NewsApiResponse.Article>()
            _state.emit(NewsScreenState.Success(resultData))
        } else {
            _state.emit(NewsScreenState.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }

    fun getCurrentDateFormatted(): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.date.toString() // Format: yyyy-MM-dd
    }
}

sealed class NewsScreenState {
    object Loading : NewsScreenState()
    data class Success(val articleList: List<NewsApiResponse.Article?>) : NewsScreenState()
    data class Error(val message: String) : NewsScreenState()
}