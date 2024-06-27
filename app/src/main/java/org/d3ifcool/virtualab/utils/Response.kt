package org.d3ifcool.virtualab.utils

import com.squareup.moshi.Json

data class Response(

	@Json(name="user_email")
	val userEmail: String? = null
)
