package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.MateriItem
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.utils.Resource

class GuruMateriViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    var materials = MutableStateFlow(emptyList<MateriItem>())
    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set
    var isRefreshing = MutableStateFlow(false)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getMyMateri()
    }

    fun getMyMateri() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = materialRepository.getMyMateri()){
                is Resource.Success -> {
                    materials.value = response.data!!
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
        getMyMateri()
        isRefreshing.value = false
    }
}