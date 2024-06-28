package org.d3ifcool.virtualab.data.model

data class Latihan(
    val exercise_id: Int,
    val title: String,
    val difficulty: String,
    val question_count: Int,
    val approval_status: String = "DRAFT",
    val author_id: Int,
)

data class ExerciseCreate(
    val title: String,
    val difficulty: String,
    val question_count: Int,
    val author_id: Int,
    val approval_status: String = "DRAFT",
)