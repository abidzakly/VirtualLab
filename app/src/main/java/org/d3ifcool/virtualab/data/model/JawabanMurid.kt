package org.d3ifcool.virtualab.data.model

data class JawabanMurid(
    val answerId: Int,
    val resultId: Int,
    val questionId: Int,
    val selectedOptionId: List<String>
)
