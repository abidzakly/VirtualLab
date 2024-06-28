package org.d3ifcool.virtualab.data.model

data class Murid(
    val student_id: Int = -1,
    val nisn: String = ""
)

data class StudentCreate(
    val nisn: String,
)