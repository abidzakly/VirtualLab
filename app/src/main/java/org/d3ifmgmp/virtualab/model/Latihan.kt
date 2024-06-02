package org.d3ifmgmp.virtualab.model

data class Latihan(
    val exerciseId: Int,
    val title: String,
    val difficulty: TingkatKesulitan,
    val questionCount: Int,
    val authorId: Int,
    val approvalStatus: ApprovalStatus = ApprovalStatus.PENDING
)

enum class TingkatKesulitan {
    EASY, MEDIUM, HARD
}