package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

class DetailLatihanViewModel(
    private val exerciseId: Int,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var latihanData = MutableStateFlow<Latihan?>(null)
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
        getLatihanDetail()
    }

    fun getLatihanDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.getDetailLatihan(exerciseId)) {
                is Resource.Success -> {
                    latihanData.value = response.data
                    apiStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun deleteLatihan() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.deleteLatihan(exerciseId)) {
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

    fun clearErrorMsg() {
        errorMessage.value = ""
    }

}