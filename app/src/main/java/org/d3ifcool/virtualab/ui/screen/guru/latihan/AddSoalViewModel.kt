package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.UserApi
import org.d3ifcool.virtualab.data.network.request.QuestionCreate
import org.d3ifcool.virtualab.data.network.response.CombinedLatihan
import retrofit2.HttpException

class AddSoalViewModel(exerciseId: Int) : ViewModel() {

    private val _latihanData = MutableStateFlow<CombinedLatihan?>(null)
    val latihanData: StateFlow<CombinedLatihan?> = _latihanData

    private val _uploadStatus = MutableStateFlow(false)
    val uploadStatus: StateFlow<Boolean> = _uploadStatus

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getCurrentLatihan(exerciseId)
    }

    private fun getCurrentLatihan(exerciseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _latihanData.value = UserApi.service.getCurrentLatihan(exerciseId)
                Log.d("AddSoalVM", "latihan Data: ${_latihanData.value}")
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun addSoal(soal: List<QuestionCreate>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = UserApi.service.addSoal(
                    soal
                ).status
                if (result) {
                    _uploadStatus.value =
                        UserApi.service.modifyLatihanStatus(soal[0].exercise_id, "PENDING").status
                }
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }

    }

    fun clearUploadStatus() {
        _uploadStatus.value = false
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}