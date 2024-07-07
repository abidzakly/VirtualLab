package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class LatihanDetail(
    @Json(name = "latihan_review")
    val latihanReview: LatihanReview? = null,
    val latihan: Latihan? = null,
    val soal: List<Soal>? = null
)

data class CombinedLatihan(
    @Json(name = "exercise_id")
    val exerciseId: Int,

    @Json(name = "question_id")
    val questionId: Int,

    @Json(name = "question_text")
    var questionText: String,

    @Json(name = "option_text")
    val optionText: List<String>,

    @Json(name = "answer_key_count")
    val answerKeyCount: Int
)

data class CombinedUser(
    @Json(name = "access_token")
    val accessToken: String? = null,
    @Json(name = "intro_title")
    val introTitle: String? = null,
    val user: User? = null,
    val student: Murid? = null,
    val teacher: Guru? = null,
)

data class CombinedUsers(
    val username: String = "",
    @Json(name = "user_id")
    val userId: Int = 0,
    val nip: String? = null,
    val nisn: String? = null,
)

data class CombinedPost(

    @Json(name = "post_id")
    val postId: Int,

    @Json(name = "approval_status")
    val approvalStatus: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "post_type")
    val postType: String,

    @Json(name = "title")
    val title: String
)

data class PendingPosts(
    @Json(name = "post_id")
    val postId: Int,
    @Json(name = "author_username")
    val authorUserName: String,
    @Json(name = "post_type")
    val postType: String,
    @Json(name = "created_at")
    val createdAt: String
)