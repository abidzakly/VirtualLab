package org.d3ifcool.virtualab.ui.screen.guru.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ArticleItem
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ArticleRepository
import org.d3ifcool.virtualab.utils.Resource

class GuruContohReaksiViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    var articles = MutableStateFlow(emptyList<ArticleItem>())
    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set
    var isRefreshing = MutableStateFlow(false)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getMyMateri()
    }

    fun getMyMateri() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = articleRepository.getMyArticle()){
                is Resource.Success -> {
                    articles.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }
    fun refreshData() {
        isRefreshing.value = true
        getMyMateri()
        isRefreshing.value = false
    }
}