package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.ExerciseUpdate
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

class AddLatihanViewModel(
    private val exerciseId: Int? = null,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var latihanDetailData = MutableStateFlow<LatihanDetail?>(null)
        private set

    var isResultsExist = MutableStateFlow<Boolean?>(null)
        private set

    var newExerciseId = MutableStateFlow<Int?>(null)
        private set

    var uploadStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var updateStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var loadingDataStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    var updatingSoal = MutableStateFlow(false)
        private set

    init {
        if (exerciseId != null) {
            newExerciseId.value = exerciseId
            getLatihanDetail()
        }
    }

    fun getLatihanDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingDataStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.getDetailLatihan(exerciseId!!)) {
                is Resource.Success -> {
                    latihanDetailData.value = response.data!!.latihanDetail
                    isResultsExist.value = response.data.isResultsExist
                    loadingDataStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    loadingDataStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun addLatihan(
        title: String,
        difficulty: String,
        questionCount: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.addLatihan(
                ExerciseCreate(
                    title,
                    difficulty,
                    questionCount
                )
            )) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    newExerciseId.value = (response.data.data as Double).toInt()
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
        updateStatus.value = ApiStatus.IDLE
        loadingDataStatus.value = ApiStatus.IDLE
        errorMessage.value = null
    }

    fun updateLatihan(
        title: String,
        difficulty: String,
        isUpdatingSoal: Boolean,
        isResettingResults: Boolean = false
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            updateStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.updateLatihan(
                newExerciseId.value!!,
                isResettingResults,
                isUpdatingSoal,
                ExerciseUpdate(title, difficulty)
            )) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    updatingSoal.value = isUpdatingSoal
                    updateStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    updateStatus.value = ApiStatus.FAILED
                }
            }
        }
    }
}
