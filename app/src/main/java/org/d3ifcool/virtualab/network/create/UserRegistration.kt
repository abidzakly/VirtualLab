package org.d3ifcool.virtualab.network.create

data class UserRegistration(
    val student: StudentCreate? = StudentCreate(nisn = ""),
    val teacher: TeacherCreate? = TeacherCreate(nip = ""),
    val user: UserCreate
)
data class UserCreate(
    val full_name: String,
    val username: String,
    val user_type: Int,
    val school: String,
    val email: String,
)

data class StudentCreate(
    val nisn: String,
)

data class TeacherCreate(
    val nip: String,
)