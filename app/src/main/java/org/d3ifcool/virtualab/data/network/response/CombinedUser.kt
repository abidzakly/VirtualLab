package org.d3ifcool.virtualab.data.network.response

import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.User

data class CombinedUser(
    val user: User? = null,
    val student: Murid? = null,
    val teacher: Guru? = null
)
