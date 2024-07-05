package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class JawabanMurid(
    @Json(name = "answer_id")
    val answerId: Int,
    @Json(name = "result_id")
    val resultId: Int,
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "selected_option")
    val selectedOption: List<String>,
    @Json(name = "is_correct")
    val isCorrect: Boolean
)

data class SubmitJawaban(
    val answers: List<JawabanItem>
)

data class JawabanItem(
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "selected_option")
    val selectedOption: List<String>
)