package org.d3ifcool.virtualab.ui.screen.murid.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.JawabanItem
import org.d3ifcool.virtualab.data.model.SoalMurid
import org.d3ifcool.virtualab.data.model.SubmitJawaban
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.utils.Resource

class MuridDetailLatihanViewModel(private val exerciseId: Int, private val studentRepository: StudentRepository): ViewModel() {

    var soal = MutableStateFlow(emptyList<SoalMurid>())
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var isUploading = MutableStateFlow(ApiStatus.IDLE)
        private set

    var answers = MutableStateFlow<Map<Int, List<String>>>(emptyMap())
        private set

    var resultId = MutableStateFlow<Int?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getSoal()
    }

    fun getSoal() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = studentRepository.getSoal(exerciseId)) {
                is Resource.Success -> {
                    soal.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun submitAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            isUploading.value = ApiStatus.LOADING
            val submitJawaban = SubmitJawaban(
                answers = answers.value.map { (questionId, selectedOption) ->
                    JawabanItem(
                        questionId = questionId,
                        selectedOption = selectedOption
                    )
                }
            )

            when (val response = studentRepository.submitAnswers(exerciseId, submitJawaban)) {
                is Resource.Success -> {
                    resultId.value = (response.data!!.data as Double).toInt()
                    isUploading.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    isUploading.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun setAnswers(questionId: Int, opsiJawaban: List<String>) {
        answers.update { currentAnswers ->
            currentAnswers.toMutableMap().apply {
                put(questionId, opsiJawaban)
            }
        }
    }

    fun clearMessage() {
        apiStatus.value = ApiStatus.IDLE
        errorMessage.value = null
    }

}