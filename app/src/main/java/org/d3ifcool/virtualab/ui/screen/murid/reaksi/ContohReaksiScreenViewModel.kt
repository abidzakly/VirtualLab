package org.d3ifcool.virtualab.ui.screen.murid.reaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ApprovedArticle
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.utils.Resource

class ContohReaksiScreenViewModel(private val studentRepository: StudentRepository): ViewModel() {

    var articleData = MutableStateFlow(emptyList<ApprovedArticle>())
        private set

    var isRefreshing = MutableStateFlow(false)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getApprovedArticles()
    }

    fun getApprovedArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = studentRepository.getApprovedArticles()) {
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

    fun refreshData() {
        isRefreshing.value = true
        getApprovedArticles()
        isRefreshing.value = false
    }

    fun clearMessage() {
        errorMessage.value = null
    }
}