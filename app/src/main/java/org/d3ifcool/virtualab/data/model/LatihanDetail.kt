package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class LatihanDetail(
    @Json(name = "exercise_id")
    val exerciseId: Int,
    val title: String,
    val difficulty: String,
    @Json(name = "question_count")
    val questionCount: Int = 0,
    @Json(name = "approval_status")
    val approvalStatus: String = "DRAFT",
    @Json(name = "author_id")
    val authorId: Int = -1,
)

data class LatihanReview(
    val title: String,
    val difficulty: String,
    @Json(name = "question_count")
    val questionCount: Int,
    @Json(name = "exercise_id")
    val exerciseId: Int,
    @Json(name = "author_username")
    val authorUserName: String,
    @Json(name = "author_nip")
    val authorNip: String,
)


data class ExerciseCreate(
    val title: String,
    val difficulty: String,
    @Json(name = "question_count")
    val questionCount: Int,
    @Json(name = "approval_status")
    val approvalStatus: String = "DRAFT",
)

data class ExerciseUpdate(
    val title: String,
    val difficulty: String
)

data class ApprovedLatihan(
    @Json(name = "exercise_id")
    val exerciseId: Int,
    val title: String,
    val difficulty: String
)