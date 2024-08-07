package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.utils.Resource

class DetailMateriViewModel(
    private val materialId: Int,
    private val materialRepository: MaterialRepository
) : ViewModel() {
    var materiData = MutableStateFlow<Materi?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var isDeleting = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getMateriDetail()
    }

    fun getMateriDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = materialRepository.getDetailMateri(materialId)) {
                is Resource.Success -> {
                    materiData.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun deleteMateri() {
        viewModelScope.launch(Dispatchers.IO) {
            isDeleting.value = ApiStatus.LOADING
            when (val response = materialRepository.deleteMateri(materialId)) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    isDeleting.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    isDeleting.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun clearStatus() {
        isDeleting.value = ApiStatus.IDLE
        errorMessage.value = null
    }
}