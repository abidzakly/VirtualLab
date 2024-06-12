package org.d3ifcool.virtualab.data.network.request


data class ExerciseCreate(
    val title: String,
    val difficulty: String,
    val question_count: Int,
    val author_id: Int,
    val approval_status: String = "DRAFT",
)