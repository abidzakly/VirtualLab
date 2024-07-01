package org.d3ifcool.virtualab.ui.screen.admin.approval.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.utils.Resource

class FileInfoViewModel(
    private val postId: Int,
    private val postType: String,
    private val materialRepository: MaterialRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {


    var data = MutableStateFlow<Any?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            val response =
                if (postType == "Materi") materialRepository.getDetailMateri(postId) else exerciseRepository.getDetailLatihan(
                    postId
                )
            when (response) {
                is Resource.Success -> {
                    data.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun approveData(id: Int, postType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                if (postType == "Materi")
                    materialRepository.approveOrReject(id, "APPROVED")
                else
                    exerciseRepository.approveOrReject(id, "APPROVED")
            when (response) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                }
            }
        }
    }
    fun rejectData(id: Int, postType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                if (postType == "Materi")
                    materialRepository.approveOrReject(id, "REJECTED")
                else
                    exerciseRepository.approveOrReject(id, "REJECTED")
            when (response) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                }
            }
        }
    }

    fun clearMessage() {
        successMessage.value = null
        errorMessage.value = null
    }
}