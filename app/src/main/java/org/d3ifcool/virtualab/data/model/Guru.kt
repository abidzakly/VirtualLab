package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Guru(
    @Json(name = "teacher_id")
    val teacherId: Int? = -1,
    val nip: String = ""
)

data class TeacherCreate(
    val nip: String,
)