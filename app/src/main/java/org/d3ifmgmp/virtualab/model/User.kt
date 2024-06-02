package org.d3ifmgmp.virtualab.model

data class User(
    var id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val password: String,
    val type: Int,
    val registrationStatus: RegistrationStatus = RegistrationStatus.PENDING,
    val school: String,
    val registrationDate: String
)


enum class RegistrationStatus {
    PENDING, APPROVED
}