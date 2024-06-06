package org.d3ifcool.virtualab.network.response

import org.d3ifcool.virtualab.model.Guru
import org.d3ifcool.virtualab.model.Murid
import org.d3ifcool.virtualab.model.User

data class CombinedUser(
    val user: User? = null,
    val student: Murid? = null,
    val teacher: Guru? = null
)
