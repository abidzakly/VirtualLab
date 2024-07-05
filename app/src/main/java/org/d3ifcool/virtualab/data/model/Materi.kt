package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Materi(
    @Json(name = "materi_review")
    val materiReview: MateriReview? = null,
    @Json(name = "materi_item")
    val materiItem: MateriItem? = null,
)

data class MateriItem(
    @Json(name = "material_id")
    val materialId: Int,
    @Json(name = "author_id")
    val authorId: Int = -1,
    @Json(name = "media_type")
    val mediaType: String = "",
    val title: String,
    val filename: String = "",
    val description: String,
    @Json(name = "approval_status")
    val approvalStatus: String
)

data class MateriReview(
    @Json(name = "material_id")
    val materialId: Int,
    @Json(name = "author_username")
    val authorUserName: String,
    @Json(name = "author_nip")
    val authorNip: String,
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val filename: String,
    val description: String,
)

data class ApprovedMateri(
    @Json(name = "material_id")
    val materialId: Int,
    val title: String,
    val description: String
)