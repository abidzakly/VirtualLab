package org.d3ifcool.virtualab.data.network.response


data class CombinedUsersResponse(
    val username: String,
    val user_id: Int,
    val nip: String? = null,
    val nisn: String? = null,
)
