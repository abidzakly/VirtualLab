package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.data.network.UserApi
import org.d3ifcool.virtualab.data.network.response.CombinedLatihan
import retrofit2.HttpException

class DetailLatihanViewModel(exerciseId: Int) : ViewModel() {

    private val _latihanData = MutableStateFlow<CombinedLatihan?>(null)
    val latihanData: StateFlow<CombinedLatihan?> = _latihanData

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getCurrentLatihan(exerciseId)
    }

    private fun getCurrentLatihan(exerciseId: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _latihanData.value = UserApi.service.getCurrentLatihan(exerciseId)
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }
    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}