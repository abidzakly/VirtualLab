package org.d3ifcool.virtualab.model

data class OpsiJawaban(
    val optionId: Int,
    val questionId: Int,
    val optionText: String,
    val isSelected: Boolean = false
)