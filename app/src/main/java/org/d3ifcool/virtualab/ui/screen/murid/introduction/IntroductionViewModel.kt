package org.d3ifcool.virtualab.ui.screen.murid.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.IntroRepository
import org.d3ifcool.virtualab.utils.Resource

class IntroductionViewModel(private val introRepository: IntroRepository) : ViewModel() {

    var introData = MutableStateFlow<Introduction?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var videoUri = MutableStateFlow<String?>(null)
        private set

    var isRefreshing = MutableStateFlow(false)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getIntroData()
    }

    fun getIntroData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = introRepository.getData()) {
                is Resource.Success -> {
                    introData.value = response.data
                    videoUri.value = ApiService.getIntroductionMedia()
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
        getIntroData()
        isRefreshing.value = false
    }
}