package org.d3ifcool.virtualab.model

data class HasilMurid(
    val resultId: Int,
    val studentId: Int,
    val exerciseId: Int,
    val score: Int,
    val completionDate: String
)