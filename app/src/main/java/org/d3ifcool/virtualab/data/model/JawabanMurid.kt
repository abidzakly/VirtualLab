package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class SubmitJawaban(
    val answers: List<JawabanItem>
)

data class JawabanItem(
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "selected_option")
    val selectedOption: List<String>
)