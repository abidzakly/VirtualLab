package org.d3ifcool.virtualab.data.network.request

data class QuestionCreate(
    val exercise_id: Int,
    val question_text: String,
    val option_text: List<String>,
    val answer_keys: List<String>
)
