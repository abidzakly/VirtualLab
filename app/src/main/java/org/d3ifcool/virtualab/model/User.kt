package org.d3ifcool.virtualab.model

data class User(
    var user_id: Int,
    val full_name: String,
    val username: String,
    val email: String,
    val password: String,
    val user_type: Int,
    val registration_status: RegistrationStatus = RegistrationStatus.pending,
    val school: String,
    val registration_date: String
)

enum class RegistrationStatus {
    pending, approved
}