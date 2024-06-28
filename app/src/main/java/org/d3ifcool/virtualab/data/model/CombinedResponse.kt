package org.d3ifcool.virtualab.data.model

import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.Soal
import org.d3ifcool.virtualab.data.model.User

data class CombinedLatihan(
    val latihan: Latihan,
    val soal: List<Soal>? = null
)

data class CombinedUser(
    val user: User? = null,
    val student: Murid? = null,
    val teacher: Guru? = null,
)

data class CombinedUsersResponse(
    val username: String,
    val user_id: Int,
    val nip: String? = null,
    val nisn: String? = null,
)