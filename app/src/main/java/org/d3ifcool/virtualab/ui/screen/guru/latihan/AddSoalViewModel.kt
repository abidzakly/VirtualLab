package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.model.QuestionCreate
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

data class AddSoalScreenState(
    val questionText: List<String> = emptyList(),
    val answersOptions: List<List<String>> = emptyList(),
    val answersKeys: List<List<String>> = emptyList(),
    val selectedAnswers: List<Int> = emptyList(),
    val isChecked: List<List<Boolean>> = emptyList()
)

class AddSoalViewModel(
    private val exerciseId: Int,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var state = MutableStateFlow(AddSoalScreenState())
        private set

    var latihanData = MutableStateFlow<LatihanDetail?>(null)
        private set

    var uploadStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var fetchStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    var listSoal = MutableStateFlow(emptyList<QuestionCreate>())
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
                    val questionCount = response.data!!.latihan!!.questionCount


//                    state.update {
//                        val newQuestionText = MutableList(questionCount) { "" }
//                        val newAnswersOptions = MutableList(questionCount) { List(4) { "" } }
//                        val newIsChecked = MutableList(questionCount) { List(4) { false } }
//                        val newSelectedAnswers = MutableList(questionCount) { 0 }
//                        listSoal.value = MutableList(questionCount) { QuestionCreate() }
//
//                        it.copy(
//                            questionText = newQuestionText,
//                            answersOptions = newAnswersOptions,
//                            isChecked = newIsChecked,
//                            selectedAnswers = newSelectedAnswers
//                        )
//
//                    }
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    fetchStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun submitSoal(soal: List<QuestionCreate>) {
        viewModelScope.launch(Dispatchers.IO) {
//            listSoal.value.withIndex().forEach { (i, it) ->
//                it.answerKeys.withIndex().forEach { (y, value) ->
//                    if (value.isEmpty()) {
//                        state.update {
//                            val newAnswersKeys = it.answersKeys.toMutableList()
//                            val answerKeys = it.answersKeys[i].toMutableList().apply {
//                                removeAt(y)
//                            }
//                            newAnswersKeys[i] = answerKeys
//                            it.copy(
//                                answersKeys = newAnswersKeys
//                            )
//                        }
//                    }
//                }
//            }
            uploadStatus.value = ApiStatus.LOADING
            when (val response = exerciseRepository.addSoal(exerciseId, soal)) {
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
            val newQuestionText = currentState.questionText.toMutableList().apply {
                set(index, text)
            }
            Log.d("AddSoalVM", "question: $newQuestionText")
            currentState.copy(
                questionText = newQuestionText,
            )
        }
    }

    fun setSelectedAnswers(index: Int, answer: Int) {
        state.update { currentState ->
            val newSelectedAnswers = currentState.selectedAnswers.toMutableList().apply {
                set(index, answer)
            }
            Log.d("AddSoalVM", "selectedAnsw: $newSelectedAnswers")
            currentState.copy(selectedAnswers = newSelectedAnswers)
        }
    }

    fun setAnswerKeys(index: Int, answerIndex: Int, key: String) {
        state.update { currentState ->
            val newAnswersKeys = currentState.answersKeys.toMutableList()
            while (newAnswersKeys.size <= index) {
                newAnswersKeys.add(mutableListOf())
            }
            val questionAnswerKeys = newAnswersKeys[index].toMutableList()
            if (answerIndex >= questionAnswerKeys.size) {
                questionAnswerKeys.addAll(List(answerIndex - questionAnswerKeys.size + 1) { "" })
            }
//            if (key.isNotEmpty()) {
            questionAnswerKeys[answerIndex] = key
            newAnswersKeys[index] = questionAnswerKeys
            Log.d("AddSoalVM", "answers keys: $newAnswersKeys")
            currentState.copy(
                answersKeys = newAnswersKeys
            )
        }
    }

    fun setAnswerOptions(index: Int, optionIndex: Int, text: String) {
        state.update { currentState ->
            val newAnswersOptions = currentState.answersOptions.toMutableList()
            val answers = newAnswersOptions[index].toMutableList().apply {
                set(optionIndex, text)
            }
            newAnswersOptions.apply {
                set(index, answers)
            }
            Log.d("AddSoalVM", "options: $newAnswersOptions")
            currentState.copy(answersOptions = newAnswersOptions)
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
            Log.d("AddSoalVM", "isChecked: $newIsChecked")
            currentState.copy(isChecked = newIsChecked)
        }
    }

//    fun setAnswerOptions(index: Int, optionIndex: Int, text: String) {
//        state.update { currentState ->
//            val newAnswersOptions = currentState.answersOptions.toMutableList()
//            while (newAnswersOptions.size <= index) {
//                newAnswersOptions.add(List(4) { "" })
//            }
//            if (index < newAnswersOptions.size) {
//                val options = newAnswersOptions[index].toMutableList()
//                if (optionIndex < options.size) {
//                    options[optionIndex] = text
//                } else {
//                    options.add(text)
//                }
//                newAnswersOptions[index] = options.toList()
//            } else {
//                // Handle case where index is out of bounds or new index needs to be added
//                newAnswersOptions.add(listOf(text))
//            }
//            currentState.copy(answersOptions = newAnswersOptions)
//        }
//    }

//    fun setAnswerKeys(index: Int, answerIndex: Int, text: String) {
//        state.update { currentState ->
//            val newAnswerKeys = currentState.answersKeys.toMutableList()
//            if (index < newAnswerKeys.size) {
//                val answers = newAnswerKeys[index].toMutableList()
//                if (answerIndex < answers.size) {
//                    answers[answerIndex] = text
//                } else {
//                    answers.add(text)
//                }
//                newAnswerKeys[index] = answers.toList()
//            } else {
//                newAnswerKeys.add(listOf(text))
//            }
//            currentState.copy(answersKeys = newAnswerKeys)
//        }
//    }


//    fun setIsChecked(index: Int, checkedIndex: Int, checked: Boolean) {
//        state.update { currentState ->
//            val newIsChecked = currentState.isChecked.toMutableList()
//            if (index < newIsChecked.size) {
//                val answers = newIsChecked[index].toMutableList()
//                if (checkedIndex < answers.size) {
//                    answers[checkedIndex] = checked
//                } else {
//                    answers.add(checked)
//                }
//                newIsChecked[index] = answers.toList()
//            } else {
//                newIsChecked.add(listOf(checked))
//            }
//            currentState.copy(isChecked = newIsChecked)
//        }
//    }

    fun setSoal(index: Int, soal: QuestionCreate) {
        listSoal.update {
            it.toMutableList().apply {
                set(index, soal)
            }
        }
        Log.d("AddSoalScreen", "soal $index added.\n soal: $soal")
    }

    fun clearStatus() {
        uploadStatus.value = ApiStatus.IDLE
        successMessage.value = null
        errorMessage.value = null
    }

}