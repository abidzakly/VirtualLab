package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Artikel(
    @Json(name = "artikel_review")
    val artikelReview: ArtikelReview? = null,
    @Json(name = "artikel_item")
    val artikelItem: ArtikelItem? = null,
)

data class ArtikelItem(
    @Json(name = "article_id")
    val articleId: Int,
    @Json(name = "author_id")
    val authorId: Int = -1,
    val title: String,
    val filename: String = "",
    val description: String,
    @Json(name = "approval_status")
    val approvalStatus: String
)

data class ArtikelReview(
    @Json(name = "article_id")
    val articleId: Int,
    @Json(name = "author_username")
    val authorUserName: String,
    @Json(name = "author_nip")
    val authorNip: String,
    val title: String,
    val filename: String,
    val description: String,
)
