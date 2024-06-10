package org.d3ifcool.virtualab.data.model

data class User(
    val full_name: String,
    val username: String,
    val user_type: Int,
    val email: String,
    val password: String? = null,
    var user_id: Int,
    val registration_status: RegistrationStatus = RegistrationStatus.PENDING,
    val school: String,
    val registration_date: String? = null
)

enum class RegistrationStatus {
    PENDING, APPROVED
}