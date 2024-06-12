package org.d3ifcool.virtualab.data.model

data class Materi(
    val materialId: Int,
    val title: String,
    val mediaType: String,
    val mediaPath: String,
    val description: String,
    val authorId: Int,
    val approvalStatus: String = "DRAFT"
)

enum class MediaType {
    IMAGE, VIDEO
}