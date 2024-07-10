package org.d3ifcool.virtualab.ui.screen.guru.artikel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3ifcool.virtualab.data.model.ArticleItem
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ArticleRepository
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.toMultipartBody

class AddContohReaksiViewModel (
    private val existedArticleId: Int? = null,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    var articleData = MutableStateFlow<ArticleItem?>(null)
        private set

    var articleId = MutableStateFlow<Int?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var uploadStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        if (existedArticleId != null) {
            articleId.value = existedArticleId
            getArticleData()
        }
    }

    fun getArticleData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = articleRepository.getDetailArticle(existedArticleId!!)) {
                is Resource.Success -> {
                    articleData.value = response.data!!.articleItem
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun addOrUpdateArticle(
        articleId: Int? = null,
        title: String? = null,
        description: String? = null,
        content: Bitmap? = null,
        isUpdate: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val titlePart = title?.toRequestBody("text/plain".toMediaTypeOrNull())
            val descPart = description?.toRequestBody("text/plain".toMediaTypeOrNull())

            uploadStatus.value = ApiStatus.LOADING

            var response: Resource<MessageResponse>? = null

            if (!isUpdate) {
                if (content != null) {
                    response =
                        articleRepository.addArticle(titlePart!!, descPart!!, content.toMultipartBody())
                }
            } else {
                response = if (content != null) {
                    articleRepository.updateArticle(
                        articleId!!,
                        titlePart,
                        descPart,
                        content.toMultipartBody()
                    )
                } else {
                    articleRepository.updateArticle(
                        articleId!!,
                        titlePart,
                        descPart,
                        null
                    )
                }
            }
            when (response!!) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    uploadStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    uploadStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun clearStatus() {
        uploadStatus.value = ApiStatus.IDLE
        apiStatus.value = ApiStatus.IDLE
        errorMessage.value = null
        successMessage.value = null
    }
}