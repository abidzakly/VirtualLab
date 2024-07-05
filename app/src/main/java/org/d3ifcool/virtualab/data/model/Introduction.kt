package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Introduction(
    @Json(name = "intro_id")
    val introId: Int,
    val title: String,
    val filename: String,
    val description: String,
)

