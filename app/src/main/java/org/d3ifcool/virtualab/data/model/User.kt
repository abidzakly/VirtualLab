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
