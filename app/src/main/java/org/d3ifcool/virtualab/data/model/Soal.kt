package org.d3ifcool.virtualab.data.model

data class Soal(
    val question_id: Int,
    val exercise_id: Int,
    var question_text: String,
    val option_text: List<String>,
    val answer_keys: List<String>
)

