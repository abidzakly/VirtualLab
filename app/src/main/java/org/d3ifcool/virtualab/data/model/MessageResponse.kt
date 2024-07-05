package org.d3ifcool.virtualab.data.model

data class MessageResponse(
    val message: String = "",
    val status: Boolean = false,
    val data: Any? = null
)
