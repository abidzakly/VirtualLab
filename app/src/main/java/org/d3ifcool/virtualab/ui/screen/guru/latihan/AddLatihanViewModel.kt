package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.repository.ExerciseRepository
import retrofit2.HttpException

class AddLatihanViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val _latihanData = MutableStateFlow<Latihan?>(null)
    val latihanData: StateFlow<Latihan?> = _latihanData

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    fun addLatihan(
        title: String,
        difficulty: String,
        questionCount: Int,
        authorId: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
//            when (val response = exerciseRepository)
//            try {
//                _latihanData.value = ApiService.latihanService.addLatihan(
//                    ExerciseCreate(
//                        title,
//                        difficulty,
//                        questionCount,
//                        authorId
//                    )
//                )
//            } catch (e: HttpException) {
//                _errorMsg.value =
//                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
//                        ?.replace("detail", "")
//                Log.d("AddLatihanVM", "error: ${_errorMsg.value}")
//            }
        }
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}