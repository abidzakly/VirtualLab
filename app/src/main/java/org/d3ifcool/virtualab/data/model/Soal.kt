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
data class QuestionCreate(
    @Json(name = "exercise_id")
    val exerciseId: Int = 0,
    @Json(name = "question_text")
    val questionText: String,
    @Json(name = "option_text")
    val optionText: List<String>,
    @Json(name = "answer_keys")
    val answerKeys: List<String>
)