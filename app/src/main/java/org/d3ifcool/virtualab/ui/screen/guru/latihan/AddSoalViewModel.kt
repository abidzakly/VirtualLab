package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.QuestionCreateOrUpdate
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

data class AddSoalState(
    val soal: MutableList<QuestionCreateOrUpdate> = mutableListOf(),
    val selectedAnswers: MutableList<Int> = mutableListOf(),
    val isChecked: MutableList<MutableList<Boolean>> = mutableListOf(),
    val answerPositions: MutableList<MutableList<Int>> = mutableListOf()
)

class AddSoalViewModel(
    private val exerciseId: Int,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var state = MutableStateFlow(AddSoalState())
        private set

    var latihanData = MutableStateFlow<Latihan?>(null)
        private set

    var uploadStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var fetchStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    var listSoal = MutableStateFlow(emptyList<QuestionCreateOrUpdate>())
        private set

    init {
        getCurrentLatihan()
    }

    fun getCurrentLatihan() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.getDetailLatihan(exerciseId)) {
                is Resource.Success -> {
                    latihanData.value = response.data
                    fetchStatus.value = ApiStatus.SUCCESS
                    val questionCount = response.data!!.latihanDetail!!.questionCount

                    val listOfQuestion: MutableList<QuestionCreateOrUpdate> =
                        MutableList(questionCount) {
                            QuestionCreateOrUpdate(
                                "",
                                MutableList(4) { "" },
                                mutableListOf()
                            )
                        }
                    val newSelectedAnswers = MutableList(questionCount) { 0 }
                    val newIsChecked = MutableList(questionCount) { MutableList(4) { false } }
                    val newAnswerPositions = MutableList(questionCount) { MutableList(3) { -1 } }

                    if (response.data.soal!!.isNotEmpty()) {
                        response.data.soal.forEachIndexed { index, soal ->
                            listOfQuestion[index] =
                                QuestionCreateOrUpdate(
                                    soal.questionText,
                                    soal.optionText,
                                    soal.answerKeys,
                                    soal.questionId
                                )
                            soal.optionText.forEachIndexed { optionIndex, s ->
                                soal.answerKeys.forEachIndexed { answerIndex, answer ->
                                    if (s == answer) {
                                        newIsChecked[index][optionIndex] = true
                                        newAnswerPositions[index][answerIndex] = optionIndex
                                    }
                                }
                            }
                            newSelectedAnswers[index] = soal.answerKeys.size
                        }
                    state.update {
                        it.copy(
                            soal = listOfQuestion,
                            selectedAnswers = newSelectedAnswers,
                            isChecked = newIsChecked,
                            answerPositions = newAnswerPositions
                        )
                    }
                    } else {
                        state.update {
                            it.copy(
                                soal = listOfQuestion,
                                selectedAnswers = newSelectedAnswers,
                                isChecked = newIsChecked,
                                answerPositions = newAnswerPositions
                            )
                        }

                    }
                    listSoal.value = MutableList(questionCount) { QuestionCreateOrUpdate() }
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    fetchStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun addSoal() {
        viewModelScope.launch(Dispatchers.IO) {
            uploadStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.addSoal(exerciseId, listSoal.value)) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    uploadStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    uploadStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun updateSoal() {
        viewModelScope.launch(Dispatchers.IO) {
            uploadStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.updateSoal(exerciseId, listSoal.value)) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    uploadStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    uploadStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun deleteLatihan() {
        viewModelScope.launch(Dispatchers.IO) {
            uploadStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.deleteLatihan(exerciseId)) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    uploadStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    uploadStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun setQuestionText(index: Int, text: String) {
        state.update { currentState ->
            val updatedSoal = currentState.soal.toMutableList().apply {
                this[index] = this[index].copy(questionText = text)
            }
            currentState.copy(
                soal = updatedSoal
            )
        }
    }

    fun setSelectedAnswers(index: Int, answerCount: Int) {
        state.update { currentState ->
            val newSelectedAnswers = currentState.selectedAnswers.toMutableList().apply {
                set(index, answerCount)
            }
            currentState.copy(selectedAnswers = newSelectedAnswers)
        }
    }

    fun setAnswerKeys(
        index: Int,
        answer: String = "",
        isRemove: Boolean = false,
        isUpdate: Boolean = false,
        position: Int = 0
    ) {
        state.update { currentState ->
            val updatedSoal = currentState.soal.toMutableList().apply {
                val keys = this[index].answerKeys.toMutableList()
                if (isRemove) {
                    keys.removeAt(position)
                } else if (isUpdate) {
                    keys[position] = answer
                } else {
                    keys.add(answer)
                }
                this[index] = this[index].copy(answerKeys = keys)
            }
            currentState.copy(soal = updatedSoal)
        }
    }

    fun setAnswerOptions(index: Int, optionIndex: Int, text: String) {
        state.update { currentState ->
            val updatedSoal = currentState.soal.toMutableList().apply {
                val options = this[index].optionText.toMutableList()
                if (optionIndex in options.indices) {
                    options[optionIndex] = text
                    this[index] = this[index].copy(optionText = options)
                }
            }
            currentState.copy(soal = updatedSoal)
        }
    }

    fun setIsChecked(index: Int, checkedIndex: Int, checked: Boolean) {
        state.update { currentState ->
            val newIsChecked = currentState.isChecked.toMutableList()
            val isCheckeds = newIsChecked[index].toMutableList().apply {
                set(checkedIndex, checked)
            }
            newIsChecked.apply {
                set(index, isCheckeds)
            }
            currentState.copy(isChecked = newIsChecked)
        }
    }

    fun setSoal(index: Int, soal: QuestionCreateOrUpdate) {
        val currentList = listSoal.value.toMutableList()
        currentList[index] = soal
        listSoal.value = currentList
    }

    fun clearStatus() {
        uploadStatus.value = ApiStatus.IDLE
        successMessage.value = null
        errorMessage.value = null
    }

}