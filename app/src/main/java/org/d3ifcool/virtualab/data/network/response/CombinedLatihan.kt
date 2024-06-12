package org.d3ifcool.virtualab.data.network.response

import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.Soal

data class CombinedLatihan(
    val latihan: Latihan,
    val soal: List<Soal>? = null
)
