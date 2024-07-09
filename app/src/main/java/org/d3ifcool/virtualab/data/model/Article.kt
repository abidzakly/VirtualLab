package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Article(
    @Json(name = "article_review")
    val articleReview: ArticleReview? = null,
    @Json(name = "article_item")
    val articleItem: ArticleItem? = null,
)

data class ArticleItem(
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

data class ApprovedArticle(
    @Json(name = "article_id")
    val articleId: Int,
    val title: String,
    @Json(name = "author_name")
    val authorName: String,
    val description: String,
)

data class ArticleReview(
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
