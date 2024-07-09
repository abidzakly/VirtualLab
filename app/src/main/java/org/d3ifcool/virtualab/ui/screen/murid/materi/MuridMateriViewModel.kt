package org.d3ifcool.virtualab.ui.screen.murid.materi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ApprovedMateri
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.utils.Resource

class MuridMateriViewModel(private val studentRepository: StudentRepository): ViewModel() {

    var materis = MutableStateFlow(emptyList<ApprovedMateri>())
        private set

    var isRefreshing = MutableStateFlow(false)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getApprovedMateris()
    }

    fun getApprovedMateris() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = studentRepository.getApprovedMateris()) {
                is Resource.Success -> {
                    materis.value = response.data!!
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
        getApprovedMateris()
        isRefreshing.value = false
    }

    fun clearMessage() {
        errorMessage.value = null
    }

}