package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Soal(
    @Json(name = "question_id")
    val questionId: Int = 0,
    @Json(name = "exercise_id")
    val exerciseId: Int = 0,
    @Json(name = "question_text")
    val questionText: String,
    @Json(name = "option_text")
    val optionText: List<String>,
    @Json(name = "answer_keys")
    val answerKeys: List<String>
)

data class SoalMurid(
    @Json(name = "exercise_id")
    val exerciseId: Int,
    @Json(name = "question_title")
    val questionTitle: String,
    @Json(name = "answer_key_count")
    val answerKeyCount: Int,
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "option_text")
    val optionText: List<String>,
)
data class QuestionCreateOrUpdate(
    @Json(name = "question_text")
    var questionText: String = "",
    @Json(name = "option_text")
    val optionText: List<String> = emptyList(),
    @Json(name = "answer_keys")
    val answerKeys: List<String> = emptyList(),
    @Json(name = "question_id")
    val questionId: Int? = null
)

//data class QuestionUpdate(
//    @Json(name = "question_id")
//    val questionId: Int,
//    @Json(name = "question_text")
//    val questionText: String = "",
//    @Json(name = "option_text")
//    val optionText: List<String>,
//    @Json(name = "answer_keys")
//    val answerKeys: List<String>
//)