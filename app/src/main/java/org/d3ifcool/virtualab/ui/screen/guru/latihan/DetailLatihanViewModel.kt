package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.repository.ExerciseRepository

class DetailLatihanViewModel(
    private val exerciseId: Int,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _latihanData = MutableStateFlow<LatihanDetail?>(null)
    val latihanData: StateFlow<LatihanDetail?> = _latihanData

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getCurrentLatihan()
    }

    private fun getCurrentLatihan() {

//        viewModelScope.launch(Dispatchers.IO) {
//            when (val response = exerciseRepository.)
//                _latihanData.value = ApiService.latihanService.getCurrentLatihan(exerciseId)
//                _errorMsg.value =
//                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
//                        ?.replace("detail", "")
//        }
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}