package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json


data class User(
    @Json(name = "full_name")
    val fullName: String = "",
    val username: String = "",
    @Json(name = "user_type")
    val userType: Int = -1,
    val email: String = "",
    @Json(name = "user_id")
    val userId: Int = -1,
    val school: String = "",
)

data class UserCreate(
    @Json(name = "full_name")
    val fullName: String,
    val username: String,
    @Json(name = "user_type")
    val userType: Int,
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
