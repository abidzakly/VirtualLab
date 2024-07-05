package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Nilai(
    @Json(name = "result_id")
    val resultId: Int,
    val title: String,
    val difficulty: String,
    val score: Float
)

data class NilaiDetail(
    @Json(name = "answers_results")
    val answersResults: List<NilaiDetailItem>,
    val score: Double
)

data class NilaiDetailItem(
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "question_title")
    val questionTitle: String,
    @Json(name = "selected_option")
    val selectedOptions: List<String>,
    @Json(name = "correct_option")
    val correctOptions: List<String>,
    val correct: Boolean
)
