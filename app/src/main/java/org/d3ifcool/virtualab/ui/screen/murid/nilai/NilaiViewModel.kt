package org.d3ifcool.virtualab.ui.screen.murid.nilai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Nilai
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.utils.Resource

class NilaiViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    var nilaiData = MutableStateFlow(emptyList<Nilai>())
        private set

    var isRefreshing = MutableStateFlow(false)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getMyNilai()
    }

    fun getMyNilai() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = studentRepository.getMyResults()) {
                is Resource.Success -> {
                    nilaiData.value = response.data!!
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
        getMyNilai()
        isRefreshing.value = false
    }

    fun clearMessage() {
        errorMessage.value = null
    }

}