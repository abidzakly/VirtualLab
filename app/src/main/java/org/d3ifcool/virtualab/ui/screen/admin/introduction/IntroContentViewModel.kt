package org.d3ifcool.virtualab.ui.screen.admin.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.IntroRepository
import org.d3ifcool.virtualab.utils.Resource

class IntroContentViewModel(private val introRepository: IntroRepository) : ViewModel() {

    var data = MutableStateFlow<Introduction?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = introRepository.getData()) {
                is Resource.Success -> {
                    data.value = response.data
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun clearMessage() {
        errorMessage.value = null
    }
}