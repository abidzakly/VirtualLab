package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Murid(
    @Json(name = "student_id")
    val studentId: Int = -1,
    val nisn: String = ""
)

data class StudentCreate(
    val nisn: String,
)