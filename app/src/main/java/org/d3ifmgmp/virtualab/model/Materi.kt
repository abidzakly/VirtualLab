package org.d3ifmgmp.virtualab.model

data class Materi(
    val materialId: Int,
    val title: String,
    val mediaType: String,
    val mediaPath: String,
    val description: String,
    val authorId: Int,
    val approvalStatus: ApprovalStatus = ApprovalStatus.PENDING
)

enum class MediaType {
    IMAGE, VIDEO
}

enum class ApprovalStatus {
    PENDING, APPROVED
}