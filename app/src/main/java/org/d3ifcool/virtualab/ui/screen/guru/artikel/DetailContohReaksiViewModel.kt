package org.d3ifcool.virtualab.ui.screen.guru.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Article
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ArticleRepository
import org.d3ifcool.virtualab.utils.Resource

class DetailContohReaksiViewModel(
    private val articleId: Int,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    var articleData = MutableStateFlow<Article?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var deleteStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getArticleDetail()
    }

    fun getArticleDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = articleRepository.getDetailArticle(articleId)) {
                is Resource.Success -> {
                    articleData.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun deleteArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStatus.value = ApiStatus.LOADING
            when (val response = articleRepository.deleteArticle(articleId)) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    deleteStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    deleteStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun clearStatus() {
        deleteStatus.value = ApiStatus.IDLE
        errorMessage.value = null
    }
}