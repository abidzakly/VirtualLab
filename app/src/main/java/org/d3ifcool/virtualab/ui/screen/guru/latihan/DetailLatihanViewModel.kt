package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

class DetailLatihanViewModel(
    private val exerciseId: Int,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var latihanData = MutableStateFlow<LatihanDetail?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)

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
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun clearErrorMsg() {
        errorMessage.value = ""
    }

}