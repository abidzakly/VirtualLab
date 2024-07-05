package org.d3ifcool.virtualab.ui.screen.murid.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.NilaiDetail
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.utils.Resource

class CekJawabanViewModel(
    private val resultId: Int,
    private val studentRepository: StudentRepository
) : ViewModel() {

    var data = MutableStateFlow<NilaiDetail?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getNilaiDetail()
    }

    fun getNilaiDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
                    Log.d("CekJawabanVM", "Response: $resultId")
            when (val response = studentRepository.getResultDetail(resultId)) {
                is Resource.Success -> {
                    Log.d("CekJawabanVM", "Response: ${response.data}")
                    data.value = response.data
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    Log.d("CekJawabanVM", "Error: ${response.message}")
                    Log.d("CekJawabanVM", "Error: ${errorMessage.value}")
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }
}