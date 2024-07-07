package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

class AddLatihanViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    var exerciseId = MutableStateFlow<Int?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.IDLE)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    fun addLatihan(
        title: String,
        difficulty: String,
        questionCount: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.addLatihan(
                ExerciseCreate(
                    title,
                    difficulty,
                    questionCount
                )
            )) {
                is Resource.Success -> {
                    exerciseId.value = (response.data!!.data as Double).toInt()
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