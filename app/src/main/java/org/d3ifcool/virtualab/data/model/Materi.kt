package org.d3ifcool.virtualab.data.model

data class Materi(
    val material_id: Int,
    val author_id: Int,
    val media_type: String,
    val title: String,
    val filename: String,
    val description: String,
)

enum class MediaType {
    IMAGE, VIDEO
}