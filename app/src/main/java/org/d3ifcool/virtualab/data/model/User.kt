package org.d3ifcool.virtualab.data.model


data class User(
    val full_name: String = "",
    val username: String = "",
    val user_type: Int = -1,
    val email: String = "",
    val password: String = "",
    var user_id: Int = -1,
    val school: String = "",
)

data class UserCreate(
    val full_name: String,
    val username: String,
    val user_type: Int,
    val school: String,
    val email: String,
)

data class UserLogin(
    val username: String,
    val password: String
)

data class UserUpdate(
    val email: String,
    val password: String
)

data class UserRegistration(
    val student: StudentCreate? = StudentCreate(nisn = ""),
    val teacher: TeacherCreate? = TeacherCreate(nip = ""),
    val user: UserCreate
)
